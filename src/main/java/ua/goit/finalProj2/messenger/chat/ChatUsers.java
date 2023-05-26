package ua.goit.finalProj2.messenger.chat;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;


@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatUsers {

    Integer chat_id;

    String userName;
}
