package com.omar.spring_io_mcp_server;

import java.util.List;

public record Conference(String name, int year, String[] dates, String location, List<Session> sessions) {
}