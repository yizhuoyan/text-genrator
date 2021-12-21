package com.yizhuoyan.txtgen.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("application")
@Data
public class ApplicationProperties {
   String fileSaveLocation=System.getProperty("user.dir")+"text-generator/vm";


   public void setFileSaveLocation(String fileSaveLocation) {
      this.fileSaveLocation = fileSaveLocation.replace("~", System.getProperty("user.dir"));
   }
}
