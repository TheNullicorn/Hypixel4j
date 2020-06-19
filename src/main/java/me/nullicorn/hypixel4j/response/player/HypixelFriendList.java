package me.nullicorn.hypixel4j.response.player;

import com.google.gson.annotations.SerializedName;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import me.nullicorn.hypixel4j.data.HypixelObject;

/**
 * Created by Ben on 6/18/20 @ 5:56 PM
 */
public class HypixelFriendList implements HypixelObject {

    @Getter
    @Setter
    protected UUID owner;

    @Getter
    protected List<Friendship> friendships;

    /**
     * @return The number of friends {@link #owner} has
     */
    public int getSize() {
        return friendships.size();
    }

    @Override
    public String toString() {
        return "HypixelFriendList{" +
            "owner=" + owner +
            ", friendships=" + friendships +
            '}';
    }

    public class Friendship implements HypixelObject {

        @Getter
        @SerializedName("_id")
        protected String id;

        @SerializedName("uuidSender")
        protected UUID senderUuid;

        @SerializedName("uuidReceiver")
        protected UUID receiverUuid;

        @Getter
        @SerializedName("started")
        protected Date startDate;

        /**
         * @return Minecraft UUID of the other player (not the owner of this friend list)
         */
        public UUID getOtherPlayerUuid() {
            return wasOutgoing()
                ? receiverUuid
                : senderUuid;
        }

        /**
         * @return Whether or not the friendship was initially requested by the owner of the friend
         * list
         */
        public boolean wasOutgoing() {
            return senderUuid == HypixelFriendList.this.owner;
        }

        @Override
        public String toString() {
            return "Friendship{" +
                "id='" + id + '\'' +
                ", senderUuid=" + senderUuid +
                ", receiverUuid=" + receiverUuid +
                ", startDate=" + startDate +
                '}';
        }
    }
}
