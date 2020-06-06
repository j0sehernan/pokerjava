package com.jh.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("config")
@Data
public class Config {
	private String pokerPathData;
	private String pokerFileNameData;
	private String pokerFileNameResult;
	private String encoding;
}
