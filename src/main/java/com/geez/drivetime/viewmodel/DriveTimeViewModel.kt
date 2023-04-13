package com.geez.drivetime.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.geez.drivetime.model.database.DriveTimeRepository
import com.geez.drivetime.model.database.DriveTimeRoomDatabase
import com.geez.drivetime.model.entities.DriveTimeClass
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DriveTimeViewModel (application: Application) : ViewModel() {

    fun insert(drive: DriveTimeClass) = viewModelScope.launch {
        repository.insertDriveData(drive)
    }
    val allDriveTime: LiveData<List<DriveTimeClass>>
    val finishDrive: LiveData<List<DriveTimeClass>>
    //val finishDrive: LiveData<DriveTimeClass>
    private var repository: DriveTimeRepository

    init {
        val driveTimeDb = DriveTimeRoomDatabase.getDatabase(application)
        val driveTimeDao = driveTimeDb.driveTimeDao()

        repository = DriveTimeRepository(driveTimeDao)
        allDriveTime = repository.allDriveTime
        finishDrive = repository.finishDrive
    }

    fun update(driveTime: DriveTimeClass) = viewModelScope.launch {
        repository.updateDriveTime(driveTime)
    }

    fun delete(id: Int) = viewModelScope.launch {
        repository.deleteById(id)
    }

    fun getUnfinishedDrive(): LiveData<List<DriveTimeClass>> {
        return Transformations.map(allDriveTime) { drives ->
            drives.filter {
                it.driveFinish == null
            }
        }
    }
}

class DriveTimeViewModelFactory(private val application: Application) : ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DriveTimeViewModel::class.java)){
            return DriveTimeViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}