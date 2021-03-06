package com.feed_the_beast.ftbquests.net;

import com.feed_the_beast.ftbquests.FTBQuests;
import com.feed_the_beast.ftbquests.quest.ChangeProgress;
import com.feed_the_beast.ftbquests.util.NetUtils;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;

/**
 * @author LatvianModder
 */
public class MessageChangeProgressResponse extends MessageBase
{
	private final UUID player;
	private final int id;
	private final ChangeProgress type;
	private final boolean notifications;

	MessageChangeProgressResponse(PacketBuffer buffer)
	{
		player = NetUtils.readUUID(buffer);
		id = buffer.readVarInt();
		type = ChangeProgress.NAME_MAP.read(buffer);
		notifications = buffer.readBoolean();
	}

	public MessageChangeProgressResponse(UUID p, int i, ChangeProgress ty, boolean n)
	{
		player = p;
		id = i;
		type = ty;
		notifications = n;
	}

	@Override
	public void write(PacketBuffer buffer)
	{
		NetUtils.writeUUID(buffer, player);
		buffer.writeVarInt(id);
		ChangeProgress.NAME_MAP.write(buffer, type);
		buffer.writeBoolean(notifications);
	}

	@Override
	public void handle(NetworkEvent.Context context)
	{
		FTBQuests.NET_PROXY.changeProgress(player, id, type, notifications);
	}
}