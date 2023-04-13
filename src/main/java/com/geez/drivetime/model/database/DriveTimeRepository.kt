package com.geez.drivetime.model.database

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.geez.drivetime.model.entities.DriveTimeClass

class DriveTimeRepository(private val driveTimeDao: DriveTimeDao) {

    val allDriveTime: LiveData<List<DriveTimeClass>> = driveTimeDao.getAllDriveTime()
    val finishDrive: LiveData<List<DriveTimeClass>> = driveTimeDao.getFinishDrive()

    @WorkerThread
    suspend fun insertDriveData(drive: DriveTimeClass){
        driveTimeDao.insertDriveDetails(drive)
    }

    @WorkerThread
    suspend fun updateDriveTime(driveTime: DriveTimeClass) {
        driveTimeDao.updateDriveTime(driveTime)
    }

    @WorkerThread
    fun deleteById(id: Int) {
        driveTimeDao.deleteById(id)
    }

    //val finishDrive: LiveData<DriveTimeClass> = driveTimeDao.getFinishDrive()

    /*@WorkerThread
    suspend fun getFinishDrive(): DriveTimeClass {
        return driveTimeDao.getFinishDrive()
    }*/
}