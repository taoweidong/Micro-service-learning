package com.taowd.config;

import java.util.List;
import java.util.Map;
import lombok.Data;

/**
 * @author Taoweidong
 */
@Data
public class InstanceNotifier {

  private BuildVersionBean buildVersion;
  private IdBean id;
  private InfoBean info;
  private boolean registered;
  private RegistrationBean registration;
  private StatusInfoBean statusInfo;
  private String statusTimestamp;
  private Map<String, Object> tags;
  private int version;
  private List<Map<String, Object>> endpoints;

  @Data
  public static class BuildVersionBean {

    /**
     * value : 1.0
     */
    private String value;

  }

  @Data
  public static class IdBean {

    private String value;
  }

  @Data
  public static class InfoBean {

    private ValuesBean values;

  }

  @Data
  public static class ValuesBean {

    private BuildBean build;

    @Data
    public static class BuildBean {

      private String version;
      private String artifact;
      private String name;
      private String group;
      private String time;

    }
  }


  /**
   * @author Taoweidong
   */
  @Data
  public static class RegistrationBean {

    private String healthUrl;
    private String managementUrl;
    private Map<String, Object> metadata;
    private String name;
    private String serviceUrl;
    private String source;


  }

  /**
   * @author Taoweidong
   */
  @Data
  public static class StatusInfoBean {

    private DetailsBean details;
    private boolean down;
    private boolean offline;
    private String status;
    private boolean unknown;
    private boolean up;


    @Data
    public static class DetailsBean {

      private String exception;
      private String message;
    }
  }
}