package com.jazzkuh.modulemanager.jda;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;

public interface IDiscordBot {

    JDA getJDA();

    Guild getGuild();

}
