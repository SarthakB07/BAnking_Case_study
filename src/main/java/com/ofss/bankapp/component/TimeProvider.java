package com.ofss.bankapp.component;

import java.time.OffsetDateTime;
import org.springframework.stereotype.Component;

@Component
public class TimeProvider {
  public OffsetDateTime now() {
    return OffsetDateTime.now();
  }
}
