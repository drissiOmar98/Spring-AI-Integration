package com.omar.spring_io_mcp_server;

public record Session(String day, String time, String title, String type, String[] speakers, String room) {
}