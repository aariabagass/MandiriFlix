package com.ariabagas.mandiriflix.data.mapper

import com.ariabagas.mandiriflix.data.network.responses.CompanyDTO
import com.ariabagas.mandiriflix.data.network.responses.CountryDTO
import com.ariabagas.mandiriflix.data.network.responses.CreditsDTO
import com.ariabagas.mandiriflix.data.network.responses.ExternalDTO
import com.ariabagas.mandiriflix.data.network.responses.GenreDTO
import com.ariabagas.mandiriflix.data.network.responses.ImageDTO
import com.ariabagas.mandiriflix.data.network.responses.ImageListDTO
import com.ariabagas.mandiriflix.data.network.responses.LanguageDTO
import com.ariabagas.mandiriflix.data.network.responses.MovieCreditsDTO
import com.ariabagas.mandiriflix.data.network.responses.MovieDTO
import com.ariabagas.mandiriflix.data.network.responses.MovieDetailDTO
import com.ariabagas.mandiriflix.data.network.responses.MovieListDTO
import com.ariabagas.mandiriflix.data.network.responses.MovieReviewDTO
import com.ariabagas.mandiriflix.data.network.responses.MovieReviewListDTO
import com.ariabagas.mandiriflix.data.network.responses.PersonDTO
import com.ariabagas.mandiriflix.data.network.responses.PersonDetailDTO
import com.ariabagas.mandiriflix.data.network.responses.PersonListDTO
import com.ariabagas.mandiriflix.data.network.responses.TvCreditsDTO
import com.ariabagas.mandiriflix.data.network.responses.TvDTO
import com.ariabagas.mandiriflix.data.network.responses.VideoDTO
import com.ariabagas.mandiriflix.data.network.responses.VideoListDTO
import com.ariabagas.mandiriflix.core.model.Company
import com.ariabagas.mandiriflix.core.model.Country
import com.ariabagas.mandiriflix.core.model.Credits
import com.ariabagas.mandiriflix.core.model.External
import com.ariabagas.mandiriflix.core.model.Genre
import com.ariabagas.mandiriflix.core.model.Image
import com.ariabagas.mandiriflix.core.model.ImageList
import com.ariabagas.mandiriflix.core.model.Language
import com.ariabagas.mandiriflix.core.model.Movie
import com.ariabagas.mandiriflix.core.model.MovieCredits
import com.ariabagas.mandiriflix.core.model.MovieDetail
import com.ariabagas.mandiriflix.core.model.MovieList
import com.ariabagas.mandiriflix.core.model.MovieReview
import com.ariabagas.mandiriflix.core.model.MovieReviewList
import com.ariabagas.mandiriflix.core.model.Person
import com.ariabagas.mandiriflix.core.model.PersonDetail
import com.ariabagas.mandiriflix.core.model.PersonList
import com.ariabagas.mandiriflix.core.model.Tv
import com.ariabagas.mandiriflix.core.model.TvCredits
import com.ariabagas.mandiriflix.core.model.Video
import com.ariabagas.mandiriflix.core.model.VideoList

fun CompanyDTO.toCompany(): Company = Company(
    name = name, originCountry = originCountry
)

fun CountryDTO.toCountry(): Country = Country(
    name = name
)

fun GenreDTO.toGenre(): Genre = Genre(
    id = id, name = name
)

fun ImageDTO.toImage(): Image = Image(
    filePath = filePath
)

fun ImageListDTO.toImageList(): ImageList = ImageList(backdrops = backdrops?.map { it.toImage() },
    posters = posters?.map { it.toImage() },
    profiles = profiles?.map { it.toImage() },
    stills = stills?.map { it.toImage() })

fun LanguageDTO.toLanguage(): Language = Language(
    englishName = englishName
)

fun MovieDTO.toMovie(): Movie = Movie(
    character = character,
    id = id,
    job = job,
    overview = overview,
    posterPath = posterPath,
    releaseDate = releaseDate,
    title = title,
    voteAverage = voteAverage
)

fun MovieCreditsDTO.toMovieCredits(): MovieCredits =
    MovieCredits(cast = cast.map { it.toMovie() }, crew = crew.map { it.toMovie() })

fun MovieDetailDTO.toMovieDetail(): MovieDetail = MovieDetail(
    budget = budget,
    credits = credits.toCredits(),
    externalIds = externalIds.toExternal(),
    genres = genres.map { it.toGenre() },
    homepage = homepage,
    id = id,
    images = images.toImageList(),
    originalTitle = originalTitle,
    overview = overview,
    posterPath = posterPath,
    productionCompanies = productionCompanies.map { it.toCompany() },
    productionCountries = productionCountries.map { it.toCountry() },
    recommendations = recommendations.toMovieList(),
    releaseDate = releaseDate,
    revenue = revenue,
    runtime = runtime,
    spokenLanguages = spokenLanguages.map { it.toLanguage() },
    status = status,
    title = title,
    videos = videos.toVideoList(),
    voteAverage = voteAverage,
    voteCount = voteCount
)

fun MovieListDTO.toMovieList(): MovieList = MovieList(
    results = results.map { it.toMovie() }, totalResults = totalResults
)

fun VideoDTO.toVideo(): Video = Video(
    key = key, name = name, publishedAt = publishedAt, site = site, type = type
)

fun VideoListDTO.toVideoList(): VideoList = VideoList(results = results.map { it.toVideo() })

fun CreditsDTO.toCredits(): Credits = Credits(cast = cast.map { it.toPerson() },
    crew = crew.map { it.toPerson() },
    guestStars = guestStars?.map { it.toPerson() })

fun PersonDTO.toPerson(): Person = Person(
    character = character,
    department = department,
    id = id,
    job = job,
    knownForDepartment = knownForDepartment,
    name = name,
    profilePath = profilePath
)

fun PersonDetailDTO.toPersonDetail(): PersonDetail = PersonDetail(
    alsoKnownAs = alsoKnownAs,
    biography = biography,
    birthday = birthday,
    deathday = deathday,
    externalIds = externalIds.toExternal(),
    gender = gender,
    homepage = homepage,
    id = id,
    images = images.toImageList(),
    knownForDepartment = knownForDepartment,
    movieCredits = movieCredits.toMovieCredits(),
    name = name,
    placeOfBirth = placeOfBirth,
    profilePath = profilePath,
    tvCredits = tvCredits.toTvCredits()
)

fun PersonListDTO.toPersonList(): PersonList = PersonList(
    results = results.map { it.toPerson() }, totalResults = totalResults
)

fun TvDTO.toTv(): Tv = Tv(
    character = character,
    firstAirDate = firstAirDate,
    id = id,
    job = job,
    name = name,
    overview = overview,
    posterPath = posterPath,
    voteAverage = voteAverage
)

fun TvCreditsDTO.toTvCredits(): TvCredits =
    TvCredits(cast = cast.map { it.toTv() }, crew = crew.map { it.toTv() })

fun ExternalDTO.toExternal(): External = External(
    facebookId = facebookId, imdbId = imdbId, instagramId = instagramId, twitterId = twitterId
)

fun MovieReviewListDTO.toMovieReviewList(): MovieReviewList = MovieReviewList(
    results = movieReviewDTOS.map { it.toMovieReview() }, totalResults = totalResults
)

fun MovieReviewDTO.toMovieReview(): MovieReview = MovieReview(
    authorName = author,
    content = content,
    id = id,
    createdAt = createdAt,
    avatarUrl = authorDetailsDTO.avatarPath
)
