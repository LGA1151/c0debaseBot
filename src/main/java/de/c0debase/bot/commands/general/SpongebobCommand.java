package de.c0debase.bot.commands.general;

import de.c0debase.bot.commands.Command;
import net.dv8tion.jda.core.entities.Message;

import java.util.concurrent.ThreadLocalRandom;

public class SpongebobCommand extends Command {

    public SpongebobCommand() {
        super("spongebob", "Macht einen normalen Satz zu einem lustigen Spongebob Satz", Category.GENERAL);
    }

    @Override
    public void execute(String[] args, Message message) {
        if(args.length == 0){
            message.getChannel().sendMessage(
                    getEmbed(message.getGuild(), message.getAuthor()).setDescription("!spongebob [msg]").build())
                    .queue();
        } else {
            StringBuilder builder = new StringBuilder();

            final ThreadLocalRandom random = ThreadLocalRandom.current();
            for(char c : String.join(" ", args).toCharArray()){
                if (random.nextBoolean()) {
                    builder.append(Character.toUpperCase(c));
                } else {
                    builder.append(Character.toLowerCase(c));
                }
            }
            message.getChannel().sendMessage(builder.toString()).queue();
        }
    }
}
