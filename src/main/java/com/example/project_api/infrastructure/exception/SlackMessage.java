package com.example.project_api.infrastructure.exception;

import java.util.List;

public class SlackMessage {
    public String channel;
    public String text;
    public List<Attachment> attachments;
    public String icon_emoji;
    public String username;
}
