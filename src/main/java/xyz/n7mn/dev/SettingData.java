package xyz.n7mn.dev;

class SettingData {

    private String DiscordToken = "";

    private String MySQLServer = "";
    private int MySQlPort = 3306;
    private String MySQLDatabase = "";
    private String MySQLOption = "?allowPublicKeyRetrieval=true&useSSL=false";
    private String MySQLUsername = "";
    private String MySQLPassword = "";

    private String[] roleList = new String[]{
            "810725772435521576",
            "810741438559682620",
            "810726916197056525",
            "810726799876423680",
            "810744661366407219"
    };

    private String[] roleDBList = new String[]{
            "admin",
            "developer",
            "moderator",
            "mapper",
            "Authenticated"
    };

    public SettingData(){}
    public SettingData(String discordToken, String mySQLServer, int mySQlPort, String mySQLDatabase, String mySQLOption, String mySQLUsername, String mySQLPassword, String[] roleList, String[] roleDBList){
        this.DiscordToken = discordToken;
        this.MySQLServer = mySQLServer;
        this.MySQlPort = mySQlPort;
        this.MySQLDatabase = mySQLDatabase;
        this.MySQLOption = mySQLOption;
        this.MySQLUsername = mySQLUsername;
        this.MySQLPassword = mySQLPassword;
        this.roleList = roleList;
        this.roleDBList = roleDBList;
    }

    public String getDiscordToken() {
        return DiscordToken;
    }

    public String[] getRoleList(){
        return roleList;
    }

    public String[] getRoleDBList() {
        return roleDBList;
    }

    public String getMySQLServer() {
        return MySQLServer;
    }

    public int getMySQlPort() {
        return MySQlPort;
    }

    public String getMySQLDatabase() {
        return MySQLDatabase;
    }

    public String getMySQLOption() {
        return MySQLOption;
    }

    public String getMySQLUsername() {
        return MySQLUsername;
    }

    public String getMySQLPassword() {
        return MySQLPassword;
    }
}
