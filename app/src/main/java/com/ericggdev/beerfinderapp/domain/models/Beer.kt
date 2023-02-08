package com.ericggdev.beerfinderapp.domain.models

data class Beer(
    val abv: Double?,
    val attenuationLevel: Double?,
    val boilVolume: BoilVolume?,
    val brewersTips: String?,
    val contributedBy: String?,
    val description: String?,
    val ebc: Double?,
    val firstBrewed: String?,
    val foodPairing: List<String>?,
    val ibu: Double?,
    val id: Int,
    val imageUrl: String?,
    val ingredients: Ingredients?,
    val method: Method?,
    val name: String?,
    val ph: Double?,
    val srm: Double?,
    val tagline: String?,
    val targetFg: Int?,
    val targetOg: Double?,
    val volume: Volume?
)

data class BoilVolume(
    val unit: String?,
    val value: Int?
)

data class Ingredients(
    val hops: List<Hop>?,
    val malt: List<Malt>?,
    val yeast: String?
)

data class Hop(
    val add: String?,
    val amount: Amount?,
    val attribute: String?,
    val name: String?
)

data class Malt(
    val amount: Amount?,
    val name: String?
)

data class Method(
    val fermentation: Fermentation?,
    val mashTemp: List<MashTemp>?,
    val twist: String?
)

data class MashTemp(
    val duration: Int?,
    val temp: Temp?
)

data class Temp(
    val unit: String?,
    val value: Int?
)

data class Volume(
    val unit: String?,
    val value: Int?
)

data class Fermentation(
    val temp: Temp?
)

data class Amount(
    val unit: String?,
    val value: Double?
)