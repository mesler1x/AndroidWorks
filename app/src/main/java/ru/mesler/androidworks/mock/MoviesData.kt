package ru.mesler.androidworks.mock

import ru.mesler.androidworks.domain.model.Country
import ru.mesler.androidworks.domain.model.Genre
import ru.mesler.androidworks.domain.model.Movie
import ru.mesler.androidworks.domain.model.Person
import ru.mesler.androidworks.domain.model.Poster
import ru.mesler.androidworks.domain.model.Premiere
import ru.mesler.androidworks.domain.model.Rating
import ru.mesler.androidworks.domain.model.Votes

object MoviesData {
    val movies = listOf(
        Movie(
            id = 1,
            name = "Крестный отец",
            rating = Rating(10.0, 10.0, 10.0),
            description = "Это первый фильм одноимённой трилогии, в которой рассказывается о семье Корлеоне при патриархе Вито Корлеоне с 1945 по 1955 год.",
            votes = Votes(2000, 2000, 2000),
            premiere = Premiere("1972-05-25"),
            poster = Poster(
                "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcQAY2xsJVIZxm3K0gNtOMr9CSCvLdr5kdo3V3pv2HMuUkTBhFzRe5-b8NDRmO1mt5S5Xp_YyQ",
                "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcQAY2xsJVIZxm3K0gNtOMr9CSCvLdr5kdo3V3pv2HMuUkTBhFzRe5-b8NDRmO1mt5S5Xp_YyQ",
            ),
            genres = listOf(Genre("Драма"), Genre("Приключения")),
            countries = listOf(Country("США")),
            persons = listOf(
                Person(
                    "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcSHxqB9kAYta_DvbVt9-8-t0YxEsUjnn_FnuEZ3jC_pV321WZV-EhNA5FK3beYTKuzdHdLdfdqgXbnhR2h1ppuGNQ",
                    "Аl pachino",
                    "Крестный отец"
                ),
                Person(
                    "https://encrypted-tbn2.gstatic.com/licensed-image?q=tbn:ANd9GcT2Dt197pZ4aEWyFL-shCwZHEUYSfkhEPn8dedwPZjrx0OVWy03etz2H1whMpv82fbdTtyWQLFRkXHZudY",
                    "Marlon Brando",
                    "Сын"
                )
            )
        ),
        Movie(
            id = 2,
            name = "Индиана Джонс: В поисках утраченного ковчега",
            rating = Rating(8.8, 9.2, 9.7),
            description = "Эпическая история о борьбе",
            votes = Votes(200, 300, 100),
            premiere = Premiere("2001-12-19"),
            poster = Poster(
                "https://www.google.com/url?sa=i&url=https%3A%2F%2Fencrypted-tbn2.gstatic.com%2Fimages%3Fq%3Dtbn%3AANd9GcQAT_nlP1y5iyNaP-68q-GYYTEC9TCAQtsUtZ_3Jw7j1eUN_DJK&psig=AOvVaw2gBHPe-diHIUHdJlM-Fs2N&ust=1741583401816000&source=images&cd=vfe&opi=89978449&ved=0CBAQjRxqFwoTCIiVv_Gd_IsDFQAAAAAdAAAAABAE",
                "https://www.google.com/url?sa=i&url=https%3A%2F%2Fencrypted-tbn2.gstatic.com%2Fimages%3Fq%3Dtbn%3AANd9GcQAT_nlP1y5iyNaP-68q-GYYTEC9TCAQtsUtZ_3Jw7j1eUN_DJK&psig=AOvVaw2gBHPe-diHIUHdJlM-Fs2N&ust=1741583401816000&source=images&cd=vfe&opi=89978449&ved=0CBAQjRxqFwoTCIiVv_Gd_IsDFQAAAAAdAAAAABAE"
            ),
            genres = listOf(Genre("Приключения")),
            countries = listOf(Country("Новая Зеландия"), Country("США")),
            persons = listOf(
                Person(
                    "https://encrypted-tbn3.gstatic.com/licensed-image?q=tbn:ANd9GcRqDPwuaDjj7UrofLNwt8ryVhP9h7yedHu50es_UvvRYoJ63S1Xp32ibJhHDKJ69-7qxJqsSW-OlUsvYlY",
                    "Harrison Ford",
                    "Indiana Jones"
                ),
                Person(
                    "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcQ2gZR1jyX5ywoAS-UrvDcJIArmvVnSDi2uJFjAMfbASRRXPrMqQg5GOFFzYCX-0dmyIn9VhaO8MZbXDRxbqV7prU-GbeyMWxJXX1xGSg",
                    "Karen Allen",
                    "Marion"
                )
            )
        ),
        Movie(
            id = 3,
            name = "Челюсти",
            rating = Rating(9.0, 9.5, 9.8),
            description = "Челюсти фильм страшный",
            votes = Votes(300, 400, 150),
            premiere = Premiere("2008-07-18"),
            poster = Poster(
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRuN3KaITQK3sjJR5H8y2LM0rxznlwnUhiQ3n3RLwq05naE0pMk",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRuN3KaITQK3sjJR5H8y2LM0rxznlwnUhiQ3n3RLwq05naE0pMk"
            ),
            genres = listOf(Genre("Экшн"), Genre("Драма")),
            countries = listOf(Country("США")),
            persons = listOf(
                Person(
                    "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcTYzh4Q4vLCYbYVkZvLmGFnXMnL3ZUeFjzcqAEQfXPLxNNdH6VYy3kDIBywIqZQMSWYXEz2wjupHM8g_aW5UY3cFQ",
                    "Richard Dreyfuss",
                    "Cooper"
                ),
                Person(
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRsrFXzEt8jJ-XdNGfacauMifiqOJBO9RiyPp53l9ZA-TIX2_SOBuWXNGCoRcZ73chV2urvhDKhY5afRFdBumfGrA",
                    "Roy Scheider",
                    "Martin"
                )
            )
        ),
    )
}