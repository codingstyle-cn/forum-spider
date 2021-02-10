package cn.codingstyle.config;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class BaseDto {
    private Long id;
    private Instant createdDate;
    private Instant lastModifiedDate;
}
