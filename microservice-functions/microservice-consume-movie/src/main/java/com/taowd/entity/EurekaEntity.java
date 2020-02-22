package com.taowd.entity;

import java.util.List;

/**
 * 注册中心服务实体
 * 
 * @author Taoweidong
 */
public class EurekaEntity {

  /**
   * host : 192.168.1.103 instanceId : DESKTOP-H5QV6OP:microservice-consume-movie:9105 instanceInfo
   * :
   * {"actionType":"ADDED","appName":"MICROSERVICE-CONSUME-MOVIE","coordinatingDiscoveryServer":false,"countryId":1,"dataCenterInfo":{"name":"MyOwn"},"dirty":false,"healthCheckUrl":"http://192.168.1.103:9105/actuator/health","healthCheckUrls":["http://192.168.1.103:9105/actuator/health"],"homePageUrl":"http://192.168.1.103:9105/","hostName":"192.168.1.103","iPAddr":"192.168.1.103","id":"DESKTOP-H5QV6OP:microservice-consume-movie:9105","instanceId":"DESKTOP-H5QV6OP:microservice-consume-movie:9105","lastDirtyTimestamp":1578233522237,"lastUpdatedTimestamp":1578233522353,"leaseInfo":{"durationInSecs":90,"evictionTimestamp":0,"registrationTimestamp":1578233522353,"renewalIntervalInSecs":30,"renewalTimestamp":1578233522353,"serviceUpTimestamp":1578233521841},"metadata":{"management.port":"9105"},"overriddenStatus":"UNKNOWN","port":9105,"sID":"na","securePort":443,"secureVipAddress":"microservice-consume-movie","status":"UP","statusPageUrl":"http://192.168.1.103:9105/actuator/info","vIPAddress":"microservice-consume-movie","version":"unknown"}
   * metadata : {"$ref":"$.instanceInfo.metadata"} port : 9105 secure : false serviceId :
   * MICROSERVICE-CONSUME-MOVIE uri : http://192.168.1.103:9105
   */
  private String host;
  private String instanceId;
  private InstanceInfoBean instanceInfo;
  private int port;
  private boolean secure;
  private String serviceId;
  private String uri;

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getInstanceId() {
    return instanceId;
  }

  public void setInstanceId(String instanceId) {
    this.instanceId = instanceId;
  }

  public InstanceInfoBean getInstanceInfo() {
    return instanceInfo;
  }

  public void setInstanceInfo(InstanceInfoBean instanceInfo) {
    this.instanceInfo = instanceInfo;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public boolean isSecure() {
    return secure;
  }

  public void setSecure(boolean secure) {
    this.secure = secure;
  }

  public String getServiceId() {
    return serviceId;
  }

  public void setServiceId(String serviceId) {
    this.serviceId = serviceId;
  }

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  public static class InstanceInfoBean {

    /**
     * actionType : ADDED appName : MICROSERVICE-CONSUME-MOVIE coordinatingDiscoveryServer : false
     * countryId : 1 dataCenterInfo : {"name":"MyOwn"} dirty : false healthCheckUrl :
     * http://192.168.1.103:9105/actuator/health healthCheckUrls :
     * ["http://192.168.1.103:9105/actuator/health"] homePageUrl : http://192.168.1.103:9105/
     * hostName : 192.168.1.103 iPAddr : 192.168.1.103 id :
     * DESKTOP-H5QV6OP:microservice-consume-movie:9105 instanceId :
     * DESKTOP-H5QV6OP:microservice-consume-movie:9105 lastDirtyTimestamp : 1578233522237
     * lastUpdatedTimestamp : 1578233522353 leaseInfo :
     * {"durationInSecs":90,"evictionTimestamp":0,"registrationTimestamp":1578233522353,"renewalIntervalInSecs":30,"renewalTimestamp":1578233522353,"serviceUpTimestamp":1578233521841}
     * metadata : {"management.port":"9105"} overriddenStatus : UNKNOWN port : 9105 sID : na
     * securePort : 443 secureVipAddress : microservice-consume-movie status : UP statusPageUrl :
     * http://192.168.1.103:9105/actuator/info vIPAddress : microservice-consume-movie version :
     * unknown
     */

    private String actionType;
    private String appName;
    private boolean coordinatingDiscoveryServer;
    private int countryId;
    private DataCenterInfoBean dataCenterInfo;
    private boolean dirty;
    private String healthCheckUrl;
    private String homePageUrl;
    private String hostName;
    private String iPAddr;
    private String id;
    private String instanceId;
    private long lastDirtyTimestamp;
    private long lastUpdatedTimestamp;
    private LeaseInfoBean leaseInfo;
    private String overriddenStatus;
    private int port;
    private String sID;
    private int securePort;
    private String secureVipAddress;
    private String status;
    private String statusPageUrl;
    private String vIPAddress;
    private String version;
    private List<String> healthCheckUrls;

    public String getActionType() {
      return actionType;
    }

    public void setActionType(String actionType) {
      this.actionType = actionType;
    }

    public String getAppName() {
      return appName;
    }

    public void setAppName(String appName) {
      this.appName = appName;
    }

    public boolean isCoordinatingDiscoveryServer() {
      return coordinatingDiscoveryServer;
    }

    public void setCoordinatingDiscoveryServer(boolean coordinatingDiscoveryServer) {
      this.coordinatingDiscoveryServer = coordinatingDiscoveryServer;
    }

    public int getCountryId() {
      return countryId;
    }

    public void setCountryId(int countryId) {
      this.countryId = countryId;
    }

    public DataCenterInfoBean getDataCenterInfo() {
      return dataCenterInfo;
    }

    public void setDataCenterInfo(DataCenterInfoBean dataCenterInfo) {
      this.dataCenterInfo = dataCenterInfo;
    }

    public boolean isDirty() {
      return dirty;
    }

    public void setDirty(boolean dirty) {
      this.dirty = dirty;
    }

    public String getHealthCheckUrl() {
      return healthCheckUrl;
    }

    public void setHealthCheckUrl(String healthCheckUrl) {
      this.healthCheckUrl = healthCheckUrl;
    }

    public String getHomePageUrl() {
      return homePageUrl;
    }

    public void setHomePageUrl(String homePageUrl) {
      this.homePageUrl = homePageUrl;
    }

    public String getHostName() {
      return hostName;
    }

    public void setHostName(String hostName) {
      this.hostName = hostName;
    }

    public String getIPAddr() {
      return iPAddr;
    }

    public void setIPAddr(String iPAddr) {
      this.iPAddr = iPAddr;
    }

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public String getInstanceId() {
      return instanceId;
    }

    public void setInstanceId(String instanceId) {
      this.instanceId = instanceId;
    }

    public long getLastDirtyTimestamp() {
      return lastDirtyTimestamp;
    }

    public void setLastDirtyTimestamp(long lastDirtyTimestamp) {
      this.lastDirtyTimestamp = lastDirtyTimestamp;
    }

    public long getLastUpdatedTimestamp() {
      return lastUpdatedTimestamp;
    }

    public void setLastUpdatedTimestamp(long lastUpdatedTimestamp) {
      this.lastUpdatedTimestamp = lastUpdatedTimestamp;
    }

    public LeaseInfoBean getLeaseInfo() {
      return leaseInfo;
    }

    public void setLeaseInfo(LeaseInfoBean leaseInfo) {
      this.leaseInfo = leaseInfo;
    }

    public String getOverriddenStatus() {
      return overriddenStatus;
    }

    public void setOverriddenStatus(String overriddenStatus) {
      this.overriddenStatus = overriddenStatus;
    }

    public int getPort() {
      return port;
    }

    public void setPort(int port) {
      this.port = port;
    }

    public String getSID() {
      return sID;
    }

    public void setSID(String sID) {
      this.sID = sID;
    }

    public int getSecurePort() {
      return securePort;
    }

    public void setSecurePort(int securePort) {
      this.securePort = securePort;
    }

    public String getSecureVipAddress() {
      return secureVipAddress;
    }

    public void setSecureVipAddress(String secureVipAddress) {
      this.secureVipAddress = secureVipAddress;
    }

    public String getStatus() {
      return status;
    }

    public void setStatus(String status) {
      this.status = status;
    }

    public String getStatusPageUrl() {
      return statusPageUrl;
    }

    public void setStatusPageUrl(String statusPageUrl) {
      this.statusPageUrl = statusPageUrl;
    }

    public String getVIPAddress() {
      return vIPAddress;
    }

    public void setVIPAddress(String vIPAddress) {
      this.vIPAddress = vIPAddress;
    }

    public String getVersion() {
      return version;
    }

    public void setVersion(String version) {
      this.version = version;
    }

    public List<String> getHealthCheckUrls() {
      return healthCheckUrls;
    }

    public void setHealthCheckUrls(List<String> healthCheckUrls) {
      this.healthCheckUrls = healthCheckUrls;
    }

    public static class DataCenterInfoBean {

      /**
       * name : MyOwn
       */

      private String name;

      public String getName() {
        return name;
      }

      public void setName(String name) {
        this.name = name;
      }
    }

    public static class LeaseInfoBean {

      /**
       * durationInSecs : 90 evictionTimestamp : 0 registrationTimestamp : 1578233522353
       * renewalIntervalInSecs : 30 renewalTimestamp : 1578233522353 serviceUpTimestamp :
       * 1578233521841
       */

      private int durationInSecs;
      private int evictionTimestamp;
      private long registrationTimestamp;
      private int renewalIntervalInSecs;
      private long renewalTimestamp;
      private long serviceUpTimestamp;

      public int getDurationInSecs() {
        return durationInSecs;
      }

      public void setDurationInSecs(int durationInSecs) {
        this.durationInSecs = durationInSecs;
      }

      public int getEvictionTimestamp() {
        return evictionTimestamp;
      }

      public void setEvictionTimestamp(int evictionTimestamp) {
        this.evictionTimestamp = evictionTimestamp;
      }

      public long getRegistrationTimestamp() {
        return registrationTimestamp;
      }

      public void setRegistrationTimestamp(long registrationTimestamp) {
        this.registrationTimestamp = registrationTimestamp;
      }

      public int getRenewalIntervalInSecs() {
        return renewalIntervalInSecs;
      }

      public void setRenewalIntervalInSecs(int renewalIntervalInSecs) {
        this.renewalIntervalInSecs = renewalIntervalInSecs;
      }

      public long getRenewalTimestamp() {
        return renewalTimestamp;
      }

      public void setRenewalTimestamp(long renewalTimestamp) {
        this.renewalTimestamp = renewalTimestamp;
      }

      public long getServiceUpTimestamp() {
        return serviceUpTimestamp;
      }

      public void setServiceUpTimestamp(long serviceUpTimestamp) {
        this.serviceUpTimestamp = serviceUpTimestamp;
      }
    }

  }

}
