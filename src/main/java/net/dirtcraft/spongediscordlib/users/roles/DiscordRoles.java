package net.dirtcraft.spongediscordlib.users.roles;

public interface DiscordRoles {
    DiscordRole OWNER =     new DiscordRole(()->null,6,'c',"Owner"      );
    DiscordRole DIRTY =     new DiscordRole(()->null,5,'e',"Manager"    );
    DiscordRole ADMIN =     new DiscordRole(()->null,4,'4',"Admin"      );
    DiscordRole MOD =       new DiscordRole(()->null,3,'b',"Moderator"  );
    DiscordRole HELPER =    new DiscordRole(()->null,2,'5',"Helper"     );
    DiscordRole STAFF =     new DiscordRole(()->null,1,'d',"Staff"      );
    DiscordRole NITRO =     new DiscordRole(()->null,0,'a',"Nitro"      );
    DiscordRole DONOR =     new DiscordRole(()->null,0,'6',"Donor"      );
    DiscordRole VERIFIED =  new DiscordRole(()->null,0,'7',"Verified"   );
    DiscordRole MUTED =     new DiscordRole(()->null,0,'0',"Muted"      );
    DiscordRole NONE =      new DiscordRole(()->null,0,'0',"Unverified" );
}
