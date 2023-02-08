package com.ericggdev.beerfinderapp.data.entity

import com.ericggdev.beerfinderapp.domain.models.Amount
import com.ericggdev.beerfinderapp.domain.models.Beer
import com.ericggdev.beerfinderapp.domain.models.BoilVolume
import com.ericggdev.beerfinderapp.domain.models.Fermentation
import com.ericggdev.beerfinderapp.domain.models.Hop
import com.ericggdev.beerfinderapp.domain.models.Ingredients
import com.ericggdev.beerfinderapp.domain.models.Malt
import com.ericggdev.beerfinderapp.domain.models.MashTemp
import com.ericggdev.beerfinderapp.domain.models.Method
import com.ericggdev.beerfinderapp.domain.models.Temp
import com.ericggdev.beerfinderapp.domain.models.Volume
import com.ericggdev.beerfinderapp.helpers.extensions.emptyString
import com.google.gson.annotations.SerializedName


data class BeerEntity(
    val abv: Double?,
    @SerializedName("attenuation_level") val attenuationLevel: Double?,
    @SerializedName("boil_volume") val boilVolume: BoilVolumeEntity?,
    @SerializedName("brewers_tips") val brewersTips: String?,
    @SerializedName("contributed_by") val contributedBy: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("ebc") val ebc: Double?,
    @SerializedName("first_brewed") val firstBrewed: String?,
    @SerializedName("food_pairing") val foodPairing: List<String>?,
    @SerializedName("ibu") val ibu: Double?,
    @SerializedName("id") val id: Int?,
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("ingredients") val ingredients: IngredientsEntity?,
    @SerializedName("method") val method: MethodEntity?,
    @SerializedName("name") val name: String?,
    @SerializedName("ph") val ph: Double?,
    @SerializedName("srm") val srm: Double?,
    @SerializedName("tagline") val tagline: String?,
    @SerializedName("target_fg") val targetFg: Int?,
    @SerializedName("target_og") val targetOg: Double?,
    @SerializedName("volume") val volume: VolumeEntity?
)

data class BoilVolumeEntity(
    @SerializedName("unit") val unit: String?,
    @SerializedName("value") val value: Int?
)

data class IngredientsEntity(
    @SerializedName("hops") val hops: List<HopEntity>?,
    @SerializedName("malt") val malt: List<MaltEntity>?,
    @SerializedName("yeast") val yeast: String?
)

data class HopEntity(
    @SerializedName("add") val add: String?,
    @SerializedName("amount") val amount: AmountEntity?,
    @SerializedName("attribute") val attribute: String?,
    @SerializedName("name") val name: String?
)

data class MaltEntity(
    @SerializedName("amount") val amount: AmountEntity?,
    @SerializedName("name") val name: String?
)

data class MethodEntity(
    @SerializedName("fermentation") val fermentation: FermentationEntity?,
    @SerializedName("mash_temp") val mashTemp: List<MashTempEntity>?,
    @SerializedName("twist") val twist: String?
)

data class MashTempEntity(
    @SerializedName("duration") val duration: Int?,
    @SerializedName("temp") val temp: TempEntity?
)

data class TempEntity(
    @SerializedName("unit") val unit: String?,
    @SerializedName("value") val value: Int?
)

data class VolumeEntity(
    @SerializedName("unit") val unit: String?,
    @SerializedName("value") val value: Int?
)

data class FermentationEntity(
    @SerializedName("temp")val temp: TempEntity?
)

data class AmountEntity(
    @SerializedName("unit") val unit: String?,
    @SerializedName("value")val value: Double?
)


fun BeerEntity.toBeer(): Beer =
    Beer(
        abv = abv ?: 0.0,
        attenuationLevel = attenuationLevel ?: 0.0,
        boilVolume = boilVolume?.toBoilVolume(),
        brewersTips = brewersTips ?: String.emptyString(),
        contributedBy = contributedBy ?: String.emptyString(),
        description = description ?: String.emptyString(),
        ebc = ebc ?: 0.0,
        firstBrewed = firstBrewed ?: String.emptyString(),
        foodPairing = foodPairing,
        ibu = ibu ?: 0.0,
        id = id ?: 0,
        imageUrl = imageUrl ?: String.emptyString(),
        ingredients = ingredients?.toIngredients(),
        method = method?.toMethod(),
        name = name ?: String.emptyString(),
        ph = ph ?: 0.0,
        srm = srm ?: 0.0,
        tagline = tagline ?: String.emptyString(),
        targetFg = targetFg ?: 0,
        targetOg = targetOg ?: 0.0,
        volume = volume?.toVolume()
    )

fun MethodEntity.toMethod(): Method =
    Method(
        fermentation = fermentation.toFermentation(),
        mashTemp = mashTemp.toMashTemps(),
        twist = twist ?: String.emptyString()
    )

fun List<MashTempEntity>?.toMashTemps(): List<MashTemp>? =
    this?.map {
        MashTemp(
            duration = it.duration ?: 0,
            temp = it.temp.toTemp()
        )
    }

fun FermentationEntity?.toFermentation(): Fermentation =
    Fermentation(
        temp = this?.temp.toTemp()
    )

fun TempEntity?.toTemp(): Temp =
    Temp(
        unit = this?.unit ?: String.emptyString(),
        value = this?.value ?: 0
    )

fun BoilVolumeEntity.toBoilVolume(): BoilVolume =
    BoilVolume(
        unit = unit ?: String.emptyString(),
        value = value ?: 0
    )

fun IngredientsEntity.toIngredients(): Ingredients =
    Ingredients(
        hops = hops.toHops(),
        malt = malt.toMalts(),
        yeast = yeast ?: String.emptyString()
    )

fun AmountEntity.toAmount(): Amount =
    Amount(
        unit = unit ?: String.emptyString(),
        value = value ?: 0.0
    )

fun VolumeEntity.toVolume(): Volume =
    Volume(
        unit = unit ?: String.emptyString(),
        value = value ?: 0
    )

fun List<HopEntity>?.toHops(): List<Hop>? =
    this?.map {
        Hop(
            add = it.add ?: String.emptyString(),
            amount = it.amount?.toAmount(),
            attribute = it.attribute ?: String.emptyString(),
            name = it.name ?: String.emptyString()
        )
    }

fun List<MaltEntity>?.toMalts(): List<Malt>? =
    this?.map {
        Malt(
            amount = it.amount?.toAmount(),
            name = it.name ?: String.emptyString()
        )
    }