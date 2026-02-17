# 💹 Spring AI Metrics Dashboard

A complete **monitoring solution for AI applications** using **Spring Boot**, **Spring AI**, **Prometheus**, and **Grafana**. Track **token usage**, **response times**, **error rates**, and **project costs** in real-time to gain full visibility into your AI application usage.
<p align="center">
  <img src="https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
  <img src="https://img.shields.io/badge/Prometheus-E6522C?style=for-the-badge&logo=prometheus&logoColor=white">
  <img src="https://img.shields.io/badge/Grafana-F46800?style=for-the-badge&logo=grafana&logoColor=white">
  <img src="https://img.shields.io/badge/OpenAI-412991?style=for-the-badge&logo=openai&logoColor=white">
</p>
---

## 🔍 Why This Project Exists

When building AI applications with **large language models**, it’s easy to **lose track of your token usage and costs**.  
This project solves that problem by providing:

- ⚡ **Real-time visibility** into your AI token consumption
- 📈 **Performance metrics** (response times, error rates)
- 💰 **Cost estimation** based on token usage
- 🛠️ **A ready-to-use Grafana dashboard** with Prometheus integration

No more running blindfolded—you’ll see exactly what your AI app is doing at any moment.

---

## 🏗️ Features

- 🔹 Track **input (prompt) vs output (completion) tokens**
- 🔹 Measure **average response time**, **p50/p90/p95/p99 latency percentiles**
- 🔹 Monitor **success & error rates** per model and operation
- 🔹 Visualize **CPU & heap memory usage**
- 🔹 Dockerized **Prometheus + Grafana stack** for easy setup
- 🔹 Optional **manual and automatic triggers** for token logging or alerts  

## 🗂️ Project Structure

```text
├── src/main/java/com/omar/spring_ai_metrics
│ ├── controller/ # REST endpoints for chat and metrics
│ 
├── docker/
│ ├── prometheus.yml # Prometheus scraping configuration
│ ├── dashboards/
│ │ ├── ai-metrics-dashboard.json
│ │ └── provisioning/
│ │ ├── dashboards/
│ │ │ └── ai-metrics-dashboard.yml
│ │ └── datasources/
│ │ └── ai-metrics-datasource.yml
│ └── docker-compose.yml # Prometheus + Grafana container orchestration
├── src/main/resources/
│ └── application.yml # Spring Boot config (OpenAI, metrics, tracing)
```

## 🚀 Accessing the Application

Once everything is up and running, you can access the different components via the following URLs:

- **🌱 Spring Boot Application:** [http://localhost:8080](http://localhost:8080)  
  Interact with the AI endpoints and test token usage.

- **📡 Prometheus Metrics:** [http://localhost:9090](http://localhost:9090)  
  Explore real-time metrics and verify that your AI app is being monitored.

- **📊 Grafana Dashboard:** [http://localhost:3000](http://localhost:3000)  
  Visualize metrics on the pre-configured dashboard.  
  **Login credentials:** `admin` / `admin`

## Creating a Grafana Dashboard

1. **Log in to Grafana**
    - Open http://localhost:3000
    - Login with username: `admin` and password: `admin`

2. **Prometheus Data Source**
    - This is automatically configured by the Docker setup
    - You can verify it at Configuration (gear icon) > Data Sources

3. **Using the Pre-configured Dashboard**
    - A dashboard is already configured and automatically loaded
    - Navigate to Dashboards > General to find "Spring AI Metrics Dashboard"
    - This dashboard includes panels for token usage, request counts, and response times

4. **Optional: Create Your Own Dashboard**
    - Click on "Dashboards" in the left sidebar
    - Click "New" > "New Dashboard"
    - Click "Add visualization"

5. **Add Token Usage Panel**
    - Select the Prometheus data source
    - Enter the following query:
      ```
      spring_ai_request_tokens_prompt_total
      ```
    - Add another query:
      ```
      spring_ai_response_tokens_completion_total
      ```
    - Set the panel title to "Token Usage"
    - Click "Apply"

6. **Add Total Requests Panel**
    - Click "Add panel"
    - Select "Stat" visualization
    - Enter the query:
      ```
      spring_ai_request_total
      ```
    - Set the panel title to "Total AI Requests"
    - Click "Apply"

7. **Add Response Time Panel**
    - Click "Add panel"
    - Select "Gauge" visualization
    - Enter the query:
      ```
      rate(spring_ai_request_duration_seconds_sum[1m]) / rate(spring_ai_request_duration_seconds_count[1m]) * 1000
      ```
    - Set the unit to "milliseconds"
    - Set the panel title to "Average Response Time"
    - Click "Apply"

8. **Save the Dashboard**
    - Click the save icon in the top right
    - Name your dashboard "Spring AI Metrics"
    - Click "Save"

## Testing the Dashboard

1. Send a http request to http://localhost:8080/api/chat with a prompt request parameter
2. Watch the metrics update in real-time on the Grafana dashboard

## 🐳 Docker Configuration Files Explained

The `docker/` directory contains all the essential files for running the monitoring infrastructure. Here's what each file does:

### 1️⃣ `prometheus.yml`
- 🟢 **Purpose:** Configuration for the Prometheus server
- 📌 Defines scraping endpoints for the Spring Boot application metrics
- ⏱️ Sets scrape intervals and evaluation timeouts

### 2️⃣ `grafana/dashboards/ai-metrics-dashboard.json`
- 🎨 **Purpose:** Pre-configured Grafana dashboard
- 📊 Contains visualizations for AI metrics:
    - Token usage (prompt & completion tokens)
    - Response times
    - Error rates
- ⚡ Automatically loaded into Grafana on startup

### 3️⃣ `grafana/provisioning/dashboards/ai-metrics-dashboard.yml`
- 🗂️ **Purpose:** Grafana dashboard provisioning configuration
- 📌 Tells Grafana where to find dashboard JSON files
- ⚡ Ensures dashboards are loaded automatically on startup

### 4️⃣ `grafana/provisioning/datasources/prometheus.yml`
- 🔗 **Purpose:** Grafana datasource configuration
- 📌 Automatically configures Prometheus as a data source
- ✅ No manual setup required in the Grafana UI

## 📊 Understanding the Metrics

Spring AI automatically tracks several important metrics to help you monitor usage and performance:

### 📝 Token Usage
- `spring_ai_request_tokens_prompt_total` – Total **prompt tokens** sent to the AI
- `spring_ai_response_tokens_completion_total` – Total **completion tokens** received from the AI

### ⚡ Request Metrics
- `spring_ai_request_total` – Total number of AI requests made
- `spring_ai_request_duration_seconds` – Duration of each AI request in seconds

> 💡 **Tip:** Monitoring token usage helps you estimate costs and optimize AI queries.
