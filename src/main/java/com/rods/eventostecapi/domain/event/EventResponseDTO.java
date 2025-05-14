package com.rods.eventostecapi.domain.event;

import java.util.Date;
import java.util.UUID;

public record EventResponseDTO(UUID id, String title, String description, Date date, String city, String uf, Boolean remote, String eventUrl, String imgUrl)  {

}
