package com.hits.itindr.mainflow.feed.data

import com.hits.itindr.mainflow.feed.domain.FeedRepository
import com.hits.itindr.mainflow.feed.swipeableCards.Profile

class MockFeedRepository : FeedRepository {
    override fun getProfiles(): List<Profile> = listOf(
        Profile(
            name = "Андрей Иванов",
            tags = listOf("Python", "Django", "REST"),
            description = "Люблю программировать на питоне. Люблю изучать питон. Люблю всё, что угодно, связанное с питоном. А еще я люблю перл.Люблю программировать на питоне. Люблю изучать питон. Люблю всё, что угодно, связанное с питоном. А еще я люблю перл.Люблю программировать на питоне. Люблю изучать питон. Люблю всё, что угодно, связанное с питоном. А еще я люблю перл.Люблю программировать на питоне. Люблю изучать питон. Люблю всё, что угодно, связанное с питоном. А еще я люблю перл.",
            imageResName = "photo"
        ),
        Profile(
            name = "Мария Смирнова",
            tags = listOf("Kotlin", "Compose", "Android", "Git", "SQL", "UML"),
            description = "Собираю мобильные продукты, люблю чистую архитектуру и команды, где можно спорить о naming-е и всё равно остаться друзьями.",
            imageResName = "photo2"
        ),
        Profile(
            name = "Илья Петров",
            tags = listOf("Java", "Spring", "PostgreSQL"),
            description = "Пишу backend, автоматизирую рутину и радуюсь, когда фича едет в прод без ночных инцидентов. Всегда за хороший code review.",
            imageResName = "photo3"
        )
    )
}
