package com.ericggdev.beerfinderapp.domain.models

import java.io.Serializable

class Beer(
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
): Serializable

 class BoilVolume(
    val unit: String?,
    val value: Int?
): Serializable

 class Ingredients(
    val hops: List<Hop>?,
    val malt: List<Malt>?,
    val yeast: String?
): Serializable

 class Hop(
    val add: String?,
    val amount: Amount?,
    val attribute: String?,
    val name: String?
): Serializable

 class Malt(
    val amount: Amount?,
    val name: String?
): Serializable

 class Method(
    val fermentation: Fermentation?,
    val mashTemp: List<MashTemp>?,
    val twist: String?
): Serializable

 class MashTemp(
    val duration: Int?,
    val temp: Temp?
): Serializable

 class Temp(
    val unit: String?,
    val value: Int?
): Serializable

 class Volume(
    val unit: String?,
    val value: Int?
): Serializable

 class Fermentation(
    val temp: Temp?
): Serializable

 class Amount(
    val unit: String?,
    val value: Double?
): Serializable