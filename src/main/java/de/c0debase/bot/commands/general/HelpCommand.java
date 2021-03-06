package de.c0debase.bot.commands.general;

import de.c0debase.bot.commands.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class HelpCommand extends Command {

    public HelpCommand() {
        super("help", "Zeigt diese Hilfe", Category.GENERAL, "hilfe");
    }

    @Override
    public void execute(final String[] args, final Message message) {
        final EmbedBuilder embedBuilder = getEmbed(message.getGuild(), message.getAuthor());
        final Collection<Command> commandCollection = bot.getCommandManager().getAvailableCommands();

        if(args.length == 0){
            embedBuilder.setTitle("Command Übersicht");
            commandCollection.stream()
                    .collect(Collectors.groupingBy(Command::getCategory))
                    .entrySet().stream().sorted((entry1, entry2) -> {
                final int sizeComparison = entry2.getValue().size() - entry1.getValue().size();
                return sizeComparison != 0 ? sizeComparison : entry1.getKey().compareTo(entry2.getKey());
            }).forEach(entry -> {
                final List<Command> commandList = entry.getValue();
                final String categoryCommands = commandList.stream()
                        .map(Command::getCommand)
                        .sorted(String::compareTo)
                        .map(string -> String.format("`%s`", string))
                        .collect(Collectors.joining("  "));
                embedBuilder.addField(entry.getKey().toString(), categoryCommands, false);
            });
        } else {
            final Optional<Command> optCommand = commandCollection.stream().filter(command -> command.getCommand().equals(args[0])).findFirst();
            if (optCommand.isPresent()) {
                final Command command = optCommand.get();
                embedBuilder.addField(command.getCommand(), command.getDescription(), false);
            } else {
                embedBuilder.addField("Command wurde nicht gefunden", "", false);
            }
        }
        message.getTextChannel().sendMessage(embedBuilder.build()).queue();
    }
}
