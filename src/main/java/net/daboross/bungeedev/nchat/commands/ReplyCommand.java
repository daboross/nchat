/*
 * Copyright (C) 2013-2014 Dabo Ross <http://www.daboross.net/>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.daboross.bungeedev.nchat.commands;

import lombok.NonNull;
import net.daboross.bungeedev.nchat.NChatPlugin;
import net.daboross.bungeedev.ncommon.ColorList;
import net.daboross.bungeedev.ncommon.utils.NUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class ReplyCommand extends Command {

    private final NChatPlugin plugin;

    public ReplyCommand(@NonNull NChatPlugin plugin) {
        super("reply", null, "r");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ColorList.REG + "Please specify a message to send.");
            sender.sendMessage(ColorList.REG + "Usage: /r <message> (Sends <message> to the last person who messaged you.)");
        } else {
            String replyToName = plugin.getReplyTracker().getReplyto(sender.getName());
            CommandSender replyTo = replyToName == null ? null : plugin.getProxy().getPlayer(replyToName);
            if (replyTo == null) {
                sender.sendMessage(ColorList.REG + "No user found to reply to.");
            } else {
                String message = NUtils.arrayToString(args, " ");
                plugin.getMessageHandler().sendPrivateMessage(sender, replyTo, message);
            }
        }
    }
}