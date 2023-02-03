import core.DiscordImpl;
import models.Message;

public class Main {
    public static void main(String[] args) {

        DiscordImpl discord = new DiscordImpl();

        Message message = new Message("asd", "bsd", 5500, "test");
        Message message2 = new Message("csd", "dsd", 5500, "pest");
        Message message3 = new Message("esd", "fsd", 4000, "mest");
        Message message4 = new Message("hsd", "isd", 10000, "dest");
        Message message5 = new Message("123", "asd", 11111, "best");

        discord.sendMessage(message);
        discord.sendMessage(message2);
        discord.sendMessage(message3);
        discord.sendMessage(message4);
        discord.size();
        System.out.println();

        System.out.print("Contains -> ");
        boolean contains = discord.contains(message5);
        System.out.println(contains);

        System.out.print("Siz -> ");
        System.out.println(discord.size());

        System.out.print("GetMessage -> ");
        System.out.println(discord.getMessage("hsd"));

        System.out.print("Delete -> ");
        discord.deleteMessage("esd");
        System.out.println("Size after deletion " + discord.size());

        System.out.print("Add reaction -> ");
        discord.reactToMessage("asd", "Smile :)");
        System.out.println();
    }
}