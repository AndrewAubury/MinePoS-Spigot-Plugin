package net.MinePoS.Objects;

import net.MinePoS.Main;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

import java.util.Set;

/**
 * Created by Andrew on 08/06/2017.
 */
public class MinePoSSender implements ConsoleCommandSender {

    private boolean showExtra;
    private String OrderID;

    public MinePoSSender(){
        showExtra = false;
    }
    public  MinePoSSender(String id){
        showExtra= true;
        OrderID = id;
    }
    public void sendMessage(String s) {

    }

    public void sendMessage(String[] strings) {

    }
    @Override
    public Server getServer() {
        return Main.getInstance().getServer();
    }
    @Override
    public String getName() {
        String temp = Main.getInstance().getName();
        if(showExtra){
            temp = temp + " Order "+OrderID;
        }
        return temp;
    }

    public boolean isConversing() {
        return false;
    }

    public void acceptConversationInput(String s) {

    }

    public boolean beginConversation(Conversation conversation) {
        return false;
    }

    public void abandonConversation(Conversation conversation) {

    }

    public void abandonConversation(Conversation conversation, ConversationAbandonedEvent conversationAbandonedEvent) {

    }

    public void sendRawMessage(String s) {

    }

    public boolean isPermissionSet(String s) {
        return false;
    }

    public boolean isPermissionSet(Permission permission) {
        return false;
    }

    public boolean hasPermission(String s) {
        return false;
    }

    public boolean hasPermission(Permission permission) {
        return false;
    }

    public PermissionAttachment addAttachment(Plugin plugin, String s, boolean b) {
        return null;
    }

    public PermissionAttachment addAttachment(Plugin plugin) {
        return null;
    }

    public PermissionAttachment addAttachment(Plugin plugin, String s, boolean b, int i) {
        return null;
    }

    public PermissionAttachment addAttachment(Plugin plugin, int i) {
        return null;
    }

    public void removeAttachment(PermissionAttachment permissionAttachment) {

    }

    public void recalculatePermissions() {

    }

    public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        return null;
    }

    public boolean isOp() {
        return true;
    }

    public void setOp(boolean b) {

    }
}
