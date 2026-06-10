package com.hits.itindr.mainflow.feed.data

import com.hits.itindr.mainflow.feed.domain.FeedRepository
import com.hits.itindr.mainflow.feed.swipeableCards.Profile

class MockFeedRepository : FeedRepository {
    override suspend fun getProfiles(): List<Profile> = listOf(
        Profile(
            id = "1",
            name = "Андрей Иванов",
            tags = listOf("Python", "Django", "REST"),
            description = "Люблю программировать на питоне. Люблю изучать питон. Люблю всё, что угодно, связанное с питоном. А еще я люблю перл.Люблю программировать на питоне. Люблю изучать питон. Люблю всё, что угодно, связанное с питоном. А еще я люблю перл.Люблю программировать на питоне. Люблю изучать питон. Люблю всё, что угодно, связанное с питоном. А еще я люблю перл.Люблю программировать на питоне. Люблю изучать питон. Люблю всё, что угодно, связанное с питоном. А еще я люблю перл.",
            imageResName = "photo"
        ),
        Profile(
            id = "2",
            name = "Мария Смирнова",
            tags = listOf("Kotlin", "Compose", "Android", "Git", "SQL", "UML"),
            description = "Собираю мобильные продукты, люблю чистую архитектуру и команды, где можно спорить о naming-е и всё равно остаться друзьями.",
            imageResName = "photo2"
        ),
        Profile(
            id = "3",
            name = "Илья Петров",
            tags = listOf("Java", "Spring", "PostgreSQL"),
            description = "Пишу backend, автоматизирую рутину и радуюсь, когда фича едет в прод без ночных инцидентов. Всегда за хороший code review.",
            imageResName = "photo3"
        )
    )

    override suspend fun likeProfile(profileId: String) = com.hits.itindr.mainflow.feed.domain.ReactionResult(false)

    override suspend fun dislikeProfile(profileId: String) = com.hits.itindr.mainflow.feed.domain.ReactionResult(false)
}
