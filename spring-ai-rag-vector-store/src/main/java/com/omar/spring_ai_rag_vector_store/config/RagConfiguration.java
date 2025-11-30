package com.omar.spring_ai_rag_vector_store.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * 🔍 RAG (Retrieval-Augmented Generation) Configuration
 * <p>
 * Configures the vector store for semantic search and document retrieval.
 * Implements persistent vector storage with automatic initialization from documents.
 * <p>
 * Responsibilities:
 * - Manages vector store lifecycle and persistence
 * - Handles document ingestion and chunking
 * - Provides semantic search capabilities for RAG applications
 * - Ensures vector store persistence across application restarts
 * <p>
 * Flow:
 * 1. Checks if vector store file exists
 * 2. If exists: Loads pre-computed vectors
 * 3. If not exists: Processes documents → chunks text → generates embeddings → saves vectors
 */
@Configuration
public class RagConfiguration {

    private static final Logger log = LoggerFactory.getLogger(RagConfiguration.class);

    /**
     * Vector store filename for persistent storage of embeddings.
     * Default: "vectorstore.json"
     */
    @Value("vectorstore.json")
    private String vectorStoreName;

    /**
     * Source document file containing the data to be vectorized.
     * Located in classpath: /data/models.json
     */
    @Value("classpath:/data/models.json")
    private Resource models;

    /**
     * Configures and initializes the SimpleVectorStore bean with embedding capabilities.
     * <p>
     * This bean implements a persistent vector store that either:
     * - Loads existing vectors from disk (if available)
     * - Or processes documents, generates embeddings, and persists them (if first run)
     *
     * @param embeddingModel The embedding model used to convert text to vectors
     * @return Configured SimpleVectorStore ready for semantic search
     * @throws IOException If document reading or file operations fail
     *
     * @apiNote Process Flow:
     *          1. Check if vector store file exists
     *          2. If EXISTS: Load pre-computed embeddings → Return store
     *          3. If NOT EXISTS:
     *             - Read source documents (models.json)
     *             - Split documents into chunks using token-based splitting
     *             - Generate embeddings for each chunk
     *             - Save vectors to persistent storage
     *             - Return initialized store
     *
     * @performance First-time initialization may take significant time depending on
     *              document size and embedding model speed
     */
    @Bean
    SimpleVectorStore simpleVectorStore(EmbeddingModel embeddingModel) throws IOException {
        // Initialize vector store with embedding model
        var simpleVectorStore = SimpleVectorStore.builder(embeddingModel).build();
        var vectorStoreFile = getVectorStoreFile();
        if (vectorStoreFile.exists()) {
            // Load existing vector store from persistent storage
            log.info("Vector Store File Exists,");
            simpleVectorStore.load(vectorStoreFile);
        } else {
            // First-time initialization: process documents and generate embeddings
            log.info("Vector Store File Does Not Exist, loading documents");

            // Read source documents with metadata
            TextReader textReader = new TextReader(models);
            textReader.getCustomMetadata().put("filename", "models.txt");  // Add file metadata
            List<Document> documents = textReader.get();

            // Split documents into chunks for better retrieval
            TextSplitter textSplitter = new TokenTextSplitter();  // Token-based chunking
            List<Document> splitDocuments = textSplitter.apply(documents);

            // Generate embeddings and add to vector store
            simpleVectorStore.add(splitDocuments);

            // Persist vector store for future use
            simpleVectorStore.save(vectorStoreFile);
            log.info("Vector store initialized and saved with {} document chunks", splitDocuments.size());
        }
        return simpleVectorStore;
    }


    /**
     * Resolves the vector store file path for persistence.
     * <p>
     * Constructs the absolute path to the vector store file in the project structure.
     * Path: [project-root]/spring-ai-rag-vector-store/src/main/resources/data/[vectorStoreName]
     *
     * @return File object representing the vector store persistence location
     */
    private File getVectorStoreFile() {
        // Define the project-relative path for vector storage
        Path path = Paths.get("spring-ai-rag-vector-store","src", "main", "resources", "data");
        String absolutePath = path.toFile().getAbsolutePath() + "/" + vectorStoreName;
        return new File(absolutePath);
    }

}