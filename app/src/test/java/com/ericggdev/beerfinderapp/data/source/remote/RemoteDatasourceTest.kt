package com.ericggdev.beerfinderapp.data.source.remote

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
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RemoteDatasourceTest {

    private val firstBeer = BeerEntity(
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
    private val secondBeer = BeerEntity(
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
    private val thirdBeer = BeerEntity(
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

    private val newBeers = listOf(firstBeer,secondBeer,thirdBeer)

    @RelaxedMockK
    private lateinit var remoteDataSource: BeerRemoteDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `getBeers() When api call then remote datasource receive data correctly`() = runTest {
        coEvery { remoteDataSource.getBeers() } returns newBeers.toMutableList()

        val initial = remoteDataSource.getBeers()

        assertEquals(initial, newBeers)
    }

    @Test
    fun `getBeersByName() When api call then remote datasource receive data correctly`() = runTest {
        coEvery { remoteDataSource.getBeersByName("Beer1") } returns listOf(newBeers.first())

        val initial = remoteDataSource.getBeersByName("Beer1")

        assertEquals(initial.first().name, newBeers.first().name)
    }
}