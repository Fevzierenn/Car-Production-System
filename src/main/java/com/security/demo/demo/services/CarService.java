package com.security.demo.demo.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.security.demo.demo.dtos.CarDto;
import com.security.demo.demo.entities.Car;
import com.security.demo.demo.exceptions.CarNotExistsException;
import com.security.demo.demo.exceptions.DuplicateCarException;
import com.security.demo.demo.repositories.CarRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j //lombok annotation for logging
public class CarService {

    private final ModelMapper modelMapper;
    private final CarRepository carRepository;
    //private final Logger logger = Logger.getLogger(CarService.class.getName()); // not necessary with @Slf4j
    public CarService(CarRepository carRepository, ModelMapper modelMapper) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
    }
    
    public Car saveCarToDB(CarDto carDto) {
        
        if(!IsCarAlreadyExists(carDto)) //if IsCarAlreadyExists(carDto) false that means carDto is not unique car and already exists.
        {
            log.error("Attempted to save duplicate car: " + carDto.toString());
            throw new DuplicateCarException("Car already exists.");
            
        }
            Car car = modelMapper.map(carDto, Car.class);
            log.info("Saving new car: " + car.toString());
            return carRepository.save(car);
       
    }

    private boolean IsCarAlreadyExists(CarDto carDto)  //if returns true-> car not exists, unique.
    {

        return carRepository.countHowManyObject(carDto.getCarType(), carDto.getModel(),
        carDto.getPerform(), carDto.getFuelType());
        
    }

    public Car getCarById(Integer carId) {
        return carRepository.findById(carId)
        .orElseThrow(() -> {
            log.warn("Car with ID {} not found", carId);
            return new CarNotExistsException("Car with ID " + carId + " does not exist.");
        });
    }


    public void deleteCarById(Integer carId) {
        Car car = getCarById(carId); // if car not exists, CarNotFoundException will throw inside getCarById not here.
        carRepository.delete(car);
        log.info("Car with ID {} successfully deleted.", carId);
      
}

    public Car updateCarInfos(int carId, CarDto carDto) {
        Car car = getCarById(carId);
        Car updatedCar = modelMapper.map(carDto, Car.class);
        updatedCar.setId(car.getId());
        log.info("updated car: "+ updatedCar.toString());
        return carRepository.save(updatedCar);
    }
    // public void deleteCarById(Integer carId) {
    //     Optional<Car> car = getCarById(carId);
    //     car.ifPresentOrElse(
    //             carRepository::delete,
    //         () -> {
    //             log.info("Cant delete the not exists car.");
    //             throw new CarNotExistsException("the car has"+ carId+ " id number not exists and cant delete.");
    //         }
    //         );
        
    // }



}
