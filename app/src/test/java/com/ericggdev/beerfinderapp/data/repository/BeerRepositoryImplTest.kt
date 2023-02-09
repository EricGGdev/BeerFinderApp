package com.ericggdev.beerfinderapp.data.repository


import com.ericggdev.beerfinderapp.data.entity.AmountEntity
import com.ericggdev.beerfinderapp.data.entity.BeerEntity
import com.ericggdev.beerfinderapp.data.entity.BoilVolumeEntity
import com.ericggdev.beerfinderapp.data.entity.FermentationEntity
import com.ericggdev.beerfinderapp.data.entity.HopEntity
import com.ericggdev.beerfinderapp.data.entity.IngredientsEntity
import com.ericggdev.beerfinderapp.data.entity.MaltEntity
import com.ericggdev.beerfinderapp.data.entity.MashTempEntity
import com.ericggdev.beerfinderapp.data.entity.MethodEntity
import com.ericggdev.beerfinderapp.data.entity.TempEntity
import com.ericggdev.beerfinderapp.data.entity.VolumeEntity
import com.ericggdev.beerfinderapp.domain.models.Amount
import com.ericggdev.beerfinderapp.domain.models.BoilVolume
import com.ericggdev.beerfinderapp.domain.models.Fermentation
import com.ericggdev.beerfinderapp.domain.models.Hop
import com.ericggdev.beerfinderapp.domain.models.Ingredients
import com.ericggdev.beerfinderapp.domain.models.Malt
import com.ericggdev.beerfinderapp.domain.models.MashTemp
import com.ericggdev.beerfinderapp.domain.models.Method
import com.ericggdev.beerfinderapp.domain.models.Temp


import com.ericggdev.beerfinderapp.data.source.remote.BeerRemoteDataSource
import com.ericggdev.beerfinderapp.domain.models.Beer
import com.ericggdev.beerfinderapp.domain.models.Volume
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BeerRepositoryImplTest {

    private val firstBeer = Beer(
        id = 1,
        name = "Beer1",
        description = "Description1",
        volume = Volume("unit1", 10),
        tagline = "tagline1",
        srm = 1.1,
        ingredients = Ingredients(
            listOf(Hop(add = "add1", amount = Amount(unit = "unit1", value = 1.1), "attribute1", "name1")),
            malt = listOf(Malt(Amount(unit = "unit1", value = 1.1), "Malt1")),
            "yeast1"
        ),
        ebc = 1.1,
        abv = 1.1,
        attenuationLevel = 1.1,
        boilVolume = BoilVolume(unit = "Unit1", 10),
        firstBrewed = "FirstBrewed1",
        brewersTips = "BrewersTips1",
        contributedBy = "Contributed1",
        foodPairing = listOf("food1", "food2", "food3"),
        ibu = 1.1,
        imageUrl = "ImageURL",
        method = Method(
            fermentation = Fermentation(Temp("unit1", 10)), mashTemp = listOf(
                MashTemp(
                    10, Temp("Temp1", 10)
                )
            ), twist = "twist1"
        ),
        ph = 1.1,
        targetFg = 10,
        targetOg = 1.1
    )
    private val secondBeer = Beer(
        id = 2,
        name = "Beer2",
        description = "Description2",
        volume = Volume("unit2", 20),
        tagline = "tagline2",
        srm = 2.2,
        ingredients = Ingredients(
            listOf(Hop(add = "add2", amount = Amount(unit = "unit2", value = 2.2), "attribute2", "name2")),
            malt = listOf(Malt(Amount(unit = "unit2", value = 2.2), "Malt2")),
            "yeast2"
        ),
        ebc = 2.2,
        abv = 2.2,
        attenuationLevel = 2.2,
        boilVolume = BoilVolume(unit = "Unit2", 20),
        firstBrewed = "FirstBrewed2",
        brewersTips = "BrewersTips2",
        contributedBy = "Contributed2",
        foodPairing = listOf("food1", "food2", "food3"),
        ibu = 2.2,
        imageUrl = "ImageURL",
        method = Method(
            fermentation = Fermentation(Temp("unit2", 20)), mashTemp = listOf(
                MashTemp(
                    20, Temp("Temp2", 20)
                )
            ), twist = "twist2"
        ),
        ph = 2.2,
        targetFg = 20,
        targetOg = 2.2
    )
    private val thirdBeer = Beer(
        id = 3,
        name = "Beer3",
        description = "Description3",
        volume = Volume("unit3", 30),
        tagline = "tagline3",
        srm = 3.3,
        ingredients = Ingredients(
            listOf(Hop(add = "add3", amount = Amount(unit = "unit3", value = 3.3), "attribute3", "name3")),
            malt = listOf(Malt(Amount(unit = "unit3", value = 3.3), "Malt3")),
            "yeast3"
        ),
        ebc = 3.3,
        abv = 3.3,
        attenuationLevel = 3.3,
        boilVolume = BoilVolume(unit = "Unit3", 30),
        firstBrewed = "FirstBrewed3",
        brewersTips = "BrewersTips3",
        contributedBy = "Contributed3",
        foodPairing = listOf("food1", "food2", "food3"),
        ibu = 3.3,
        imageUrl = "ImageURL",
        method = Method(
            fermentation = Fermentation(Temp("unit3", 30)), mashTemp = listOf(
                MashTemp(
                    30, Temp("Temp2", 30)
                )
            ), twist = "twist3"
        ),
        ph = 3.3,
        targetFg = 30,
        targetOg = 3.3
    )

    private val firstBeerEntity = BeerEntity(
        id = 1,
        name = "Beer1",
        description = "Description1",
        volume = VolumeEntity("unit1", 10),
        tagline = "tagline1",
        srm = 1.1,
        ingredients = IngredientsEntity(
            listOf(HopEntity(add = "add1", amount = AmountEntity(unit = "unit1", value = 1.1), "attribute1", "name1")),
            malt = listOf(MaltEntity(AmountEntity(unit = "unit1", value = 1.1), "Malt1")),
            "yeast1"
        ),
        ebc = 1.1,
        abv = 1.1,
        attenuationLevel = 1.1,
        boilVolume = BoilVolumeEntity(unit = "Unit1", 10),
        firstBrewed = "FirstBrewed1",
        brewersTips = "BrewersTips1",
        contributedBy = "Contributed1",
        foodPairing = listOf("food1", "food2", "food3"),
        ibu = 1.1,
        imageUrl = "ImageURL",
        method = MethodEntity(
            fermentation = FermentationEntity(TempEntity("unit1", 10)), mashTemp = listOf(
                MashTempEntity(
                    10, TempEntity("Temp1", 10)
                )
            ), twist = "twist1"
        ),
        ph = 1.1,
        targetFg = 10,
        targetOg = 1.1
    )
    private val secondBeerEntity = BeerEntity(
        id = 2,
        name = "Beer2",
        description = "Description2",
        volume = VolumeEntity("unit2", 20),
        tagline = "tagline2",
        srm = 2.2,
        ingredients = IngredientsEntity(
            listOf(HopEntity(add = "add2", amount = AmountEntity(unit = "unit2", value = 2.2), "attribute2", "name2")),
            malt = listOf(MaltEntity(AmountEntity(unit = "unit2", value = 2.2), "Malt2")),
            "yeast2"
        ),
        ebc = 2.2,
        abv = 2.2,
        attenuationLevel = 2.2,
        boilVolume = BoilVolumeEntity(unit = "Unit2", 20),
        firstBrewed = "FirstBrewed2",
        brewersTips = "BrewersTips2",
        contributedBy = "Contributed2",
        foodPairing = listOf("food1", "food2", "food3"),
        ibu = 2.2,
        imageUrl = "ImageURL",
        method = MethodEntity(
            fermentation = FermentationEntity(TempEntity("unit2", 20)), mashTemp = listOf(
                MashTempEntity(
                    20, TempEntity("Temp2", 20)
                )
            ), twist = "twist2"
        ),
        ph = 2.2,
        targetFg = 20,
        targetOg = 2.2
    )
    private val thirdBeerEntity = BeerEntity(
        id = 3,
        name = "Beer3",
        description = "Description3",
        volume = VolumeEntity("unit3", 30),
        tagline = "tagline3",
        srm = 3.3,
        ingredients = IngredientsEntity(
            listOf(HopEntity(add = "add3", amount = AmountEntity(unit = "unit3", value = 3.3), "attribute3", "name3")),
            malt = listOf(MaltEntity(AmountEntity(unit = "unit3", value = 3.3), "Malt3")),
            "yeast3"
        ),
        ebc = 3.3,
        abv = 3.3,
        attenuationLevel = 3.3,
        boilVolume = BoilVolumeEntity(unit = "Unit3", 30),
        firstBrewed = "FirstBrewed3",
        brewersTips = "BrewersTips3",
        contributedBy = "Contributed3",
        foodPairing = listOf("food1", "food2", "food3"),
        ibu = 3.3,
        imageUrl = "ImageURL",
        method = MethodEntity(
            fermentation = FermentationEntity(TempEntity("unit3", 30)), mashTemp = listOf(
                MashTempEntity(
                    30, TempEntity("Temp2", 30)
                )
            ), twist = "twist3"
        ),
        ph = 3.3,
        targetFg = 30,
        targetOg = 3.3
    )

    private val newBeers = listOf(firstBeer, secondBeer, thirdBeer)
    private val newBeersEntity = listOf(firstBeerEntity, secondBeerEntity, thirdBeerEntity)

    @RelaxedMockK
    private var remoteDataSource = mockk<BeerRemoteDataSource>(relaxed = true)

    private var beerRepository= BeerRepositoryImpl(remoteDataSource)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }


    @Test
    fun `getBeers() When api call then repository receive data correctly`() = runTest {
        coEvery { beerRepository.getBeers(1) } returns newBeers.toMutableList()

        val initial = beerRepository.getBeers(1)

        assertEquals(initial, newBeers)
    }

    @Test
    fun `getBeersByName() When api call then repository receive data correctly`() = runTest {
        coEvery { beerRepository.getBeersByName("Beer1") } returns listOf(newBeers.first())

        val initial = beerRepository.getBeersByName("Beer1")

        assertEquals(initial.first().name, newBeers.first().name)
    }
}