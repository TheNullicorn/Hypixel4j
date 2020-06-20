package me.nullicorn.hypixel4j.response.player;

import com.google.gson.annotations.SerializedName;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import me.nullicorn.hypixel4j.data.HypixelObject;

/**
 * Represents a Hypixel player's friend list
 */
public class HypixelFriendList implements HypixelObject {

    /**
     * The UUID of the player whose friendships are stored in this class
     *
     * @see #friendships
     */
    @Getter
    @Setter
    protected UUID ownerUuid;

    /**
     * A list of friendships between the owner of this friend list and other players
     */
    @Getter
    @Setter
    protected List<Friendship> friendships;

    /**
     * @return The number of friends {@link #ownerUuid} has
     */
    public int getSize() {
        return friendships.size();
    }

    @Override
    public String toString() {
        return "HypixelFriendList{" +
            "ownerUuid=" + ownerUuid +
            ", friendships=" + friendships +
            '}';
    }

    /**
     * Represents a friendship between two players on Hypixel
     */
    public static class Friendship implements HypixelObject {

        /**
         * The friend list containing this frienship instance
         */
        @Setter
        private HypixelFriendList friendList;

        /**
         * The internal ID of this Hypixel friendship
         */
        @Getter
        @SerializedName("_id")
        protected String id;

        /**
         * The player responsible for creating this friendship
         */
        @SerializedName("uuidSender")
        protected UUID senderUuid;

        /**
         * The player who the original friend request was sent to
         */
        @SerializedName("uuidReceiver")
        protected UUID receiverUuid;

        /**
         * The date at which this friendship was started; if these two players have been friends
         * before, this is the most recent date at which a friend request was accepted between them
         */
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
            return senderUuid.equals(friendList.ownerUuid);
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
