package me.alii.commands;

import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
import com.hypixel.hytale.server.core.entity.entities.Player;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import java.util.Set;

public class FirstCommand extends CommandBase {

    public FirstCommand() {
        super("apply-row-filter", "Command to apply chest row filters");
    }

    @Override
    protected void executeSync(@NonNullDecl CommandContext commandContext) {
        if (!(commandContext.sender() instanceof Player player)) return;

    }

    @NonNullDecl
    @Override
    public Set<String> getAliases() {
        return Set.of("arf");
    }
}