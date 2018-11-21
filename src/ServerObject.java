
public class ServerObject {

  private  String name;
  private   String ip;
  private   String suffix;
  private   int portNo;

    public ServerObject(String name, String ip, String suffix,int portNo) {
        this.name = name;
        this.ip = ip;
        this.suffix = suffix;
        this.portNo = portNo;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }


    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSuffix() {
        return suffix;
    }

    public int getPortNo() {
        return portNo;
    }

    public void setPortNo(int portNo) {
        this.portNo = portNo;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

}