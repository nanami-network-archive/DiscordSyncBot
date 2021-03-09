package xyz.n7mn.dev;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

class DiscordListener extends ListenerAdapter {

    private final Timer timer;
    private final SettingData data;

    public DiscordListener(SettingData data, Timer timer){
        this.data = data;
        this.timer = timer;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.isWebhookMessage()){
            return;
        }
        if (event.getAuthor().isBot()){
            return;
        }

        if (!event.getMessage().getContentRaw().toLowerCase().startsWith("7.")){
            return;
        }

        String s = event.getMessage().getContentRaw().toLowerCase();
        Message message = event.getMessage();

        message.reply("準備中").queue();
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {

        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                new Thread(()->{
                    JDA jda = event.getJDA();
                    Guild guild = jda.getGuildById("810725404545515561");

                    List<Member> memberList = guild.getMembers();
                    String[] roleList = data.getRoleList();
                    String[] roleDBList = data.getRoleDBList();

                    for (Member member : memberList){
                        String discordRole = "";
                        List<Role> roles = member.getRoles();

                        for (Role role : roles){
                            for (int i = 0; i < roleList.length; i++){
                                if (roleList[i].equals(role.getId())){
                                    discordRole = roleDBList[i];
                                    break;
                                }
                            }

                            if (discordRole.equals("")){
                                continue;
                            }
                            break;
                        }

                        System.out.println(discordRole);

                        try {
                            boolean found = false;

                            Connection con = DriverManager.getConnection("jdbc:mysql://" + data.getMySQLServer() + ":" + data.getMySQlPort() + "/" + data.getMySQLDatabase() + data.getMySQLOption(), data.getMySQLUsername(), data.getMySQLPassword());
                            PreparedStatement statement = con.prepareStatement("SELECT * FROM MinecraftUserList WHERE DiscordUserID = ?");
                            statement.setString(1, member.getId());
                            ResultSet set = statement.executeQuery();
                            if (set.next()){
                                found = true;

                                PreparedStatement s = con.prepareStatement("UPDATE MinecraftUserList SET Role = ? WHERE ID = ?");
                                s.setString(1, discordRole);
                                s.setString(2, set.getString("ID"));
                                s.execute();
                                s.close();
                            }
                            set.close();
                            statement.close();
                            if (found){
                                con.close();
                                continue;
                            }

                            con.close();
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                }).start();
            }
        };


        timer.scheduleAtFixedRate(task, 0, 120000L);

    }
}
