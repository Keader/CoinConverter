package dev.keader.coinconversor.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import dev.keader.coinconversor.database.model.Currency

@Dao
interface CurrencyDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(currency: Currency)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(currencies: List<Currency>)

    @Query("SELECT * FROM Currency ORDER BY name ASC")
    fun getAllCurrenciesOrderByName() : LiveData<List<Currency>>

    @Query("SELECT * FROM Currency ORDER BY code ASC")
    fun getAllCurrenciesOrderByCode() : LiveData<List<Currency>>

    // || in SQL = String concat
    @Query("SELECT * FROM Currency WHERE (code LIKE '%' || :search || '%') OR (name LIKE '%' || :search || '%') ORDER BY code ASC")
    fun getAllCurrenciesBySearch(search: String) : LiveData<List<Currency>>

    @Query("DELETE FROM Currency")
    suspend fun clearCurrencies()

    @Transaction
    suspend fun clearAndInsert(currencies: List<Currency>) {
        clearCurrencies()
        insert(currencies)
    }
}
