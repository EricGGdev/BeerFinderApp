package com.ericggdev.beerfinderapp.data.entity

import com.ericggdev.beerfinderapp.domain.models.Beer


data class BeerEntity(
    val abv: Double,
    val attenuation_level: Double,
    val boil_volume: BoilVolume,
    val brewers_tips: String,
    val contributed_by: String,
    val description: String,
    val ebc: Int,
    val first_brewed: String,
    val food_pairing: List<String>,
    val ibu: Double,
    val id: Int,
    val image_url: String,
    val ingredients: Ingredients,
    val method: Method,
    val name: String,
    val ph: Double,
    val srm: Double,
    val tagline: String,
    val target_fg: Int,
    val target_og: Double,
    val volume: Volume
)

data class BoilVolume(
    val unit: String,
    val value: Int
)

data class Ingredients(
    val hops: List<Hop>,
    val malt: List<Malt>,
    val yeast: String
)

data class Hop(
    val add: String,
    val amount: Amount,
    val attribute: String,
    val name: String
)

data class Malt(
    val amount: Amount,
    val name: String
)

data class Method(
    val fermentation: Fermentation,
    val mash_temp: List<MashTemp>,
    val twist: String
)

data class MashTemp(
    val duration: Int,
    val temp: Temp
)

data class Temp(
    val unit: String,
    val value: Int
)

data class Volume(
    val unit: String,
    val value: Int
)

data class Fermentation(
    val temp: Temp
)

data class Amount(
    val unit: String,
    val value: Double
)



fun BeerEntity.toBeer(): Beer =
    Beer(
         abv = abv ?:0.0,
        attenuation_level = attenuation_level ?:0.0,
        boil_volume = boil_volume.toBoilVolume(),
        brewers_tips = brewers_tips ?:"",
        contributed_by = contributed_by ?:"",
         description = description ?:"",
        ebc = ebc ?:0,
        first_brewed = first_brewed ?:"",
        food_pairing = food_pairing,
        ibu = ibu ?:0.0,
        id = id ?:0,
        image_url = image_url ?:"",
        ingredients = ingredients.toIngredients(),
        method = method.toMethod(),
        name = name ?:"",
        ph = ph ?:0.0,
        srm = srm ?:0.0,
        tagline = tagline ?:"",
        target_fg = target_fg ?:0,
        target_og = target_og ?:0.0,
        volume = volume.toVolume()
    )

fun Method.toMethod():com.ericggdev.beerfinderapp.domain.models.Method =
    com.ericggdev.beerfinderapp.domain.models.Method(
        fermentation = fermentation.toFermentation(),
        mash_temp = mash_temp.toMashTemps(),
        twist = twist?:""
    )

fun List<MashTemp>.toMashTemps():List<com.ericggdev.beerfinderapp.domain.models.MashTemp> =
    this.map {
        com.ericggdev.beerfinderapp.domain.models.MashTemp(
            duration = it.duration ?:0,
            temp = it.temp.toTemp()
        )
    }

fun Fermentation.toFermentation():com.ericggdev.beerfinderapp.domain.models.Fermentation =
    com.ericggdev.beerfinderapp.domain.models.Fermentation(
        temp = temp.toTemp()
    )
fun Temp.toTemp():com.ericggdev.beerfinderapp.domain.models.Temp =
    com.ericggdev.beerfinderapp.domain.models.Temp(
        unit = unit?:"",
        value = value ?:0
    )
fun BoilVolume.toBoilVolume():com.ericggdev.beerfinderapp.domain.models.BoilVolume =
    com.ericggdev.beerfinderapp.domain.models.BoilVolume(
        unit = unit?:"",
        value = value ?:0
    )

fun Ingredients.toIngredients():com.ericggdev.beerfinderapp.domain.models.Ingredients =
    com.ericggdev.beerfinderapp.domain.models.Ingredients(
        hops = hops.toHops(),
        malt = malt.toMalts(),
        yeast = yeast?:""
    )

fun Amount.toAmount():com.ericggdev.beerfinderapp.domain.models.Amount =
    com.ericggdev.beerfinderapp.domain.models.Amount(
        unit = unit?:"",
        value = value ?:0.0
    )
fun Volume.toVolume():com.ericggdev.beerfinderapp.domain.models.Volume =
    com.ericggdev.beerfinderapp.domain.models.Volume(
        unit = unit?:"",
        value = value ?:0
    )
fun List<Hop>.toHops():List<com.ericggdev.beerfinderapp.domain.models.Hop> =
    this.map {
        com.ericggdev.beerfinderapp.domain.models.Hop(
            add = it.add ?:"",
            amount = it.amount.toAmount(),
            attribute = it.attribute ?:"",
            name = it.name ?:""
        )
    }

fun List<Malt>.toMalts():List<com.ericggdev.beerfinderapp.domain.models.Malt> =
    this.map {
        com.ericggdev.beerfinderapp.domain.models.Malt(
            amount = it.amount.toAmount(),
            name = it.name ?:""
        )
    }