import React, { useEffect, useState } from 'react';
import { View, StyleSheet, ScrollView, Text } from 'react-native';
import { FormBuilder } from 'react-native-paper-form-builder';
import { useForm } from 'react-hook-form';
import { Button } from 'react-native-paper';
import BookingService from '../services/BookingService';
import { BASE_SERVICE_URL } from '../config';

const bookingService = new BookingService();
function BookingComponent() {
  const [cubicle,setcubicle] = useState([]);
  const [employee,setemployee] = useState([]);
  const CreateBooking = (data: { cubicleId: string; employeeId:string; startDate: string; endDate:string;}) => {
    bookingService.createBooking(data.cubicleId, data.employeeId,data.startDate,data.endDate).then();
  };
  const { control, setFocus, formState: { errors }, handleSubmit } = useForm({
    mode: 'onChange',
  });

  useEffect(() => {
      EmployeeIds();
      CubicleIds();
  },[])
 const EmployeeIds = async () => {
 fetch(BASE_SERVICE_URL + '/employee/getEmployeeIds', {
  method: 'get',
  headers:{"Access-Control-Allow-Origin": "*", 'Content-Type': 'application/json','Authorization': "Bearer " + localStorage.getItem('token')},
            }).then((response) => {
              return response.json();
          }).then(dat => {
            setemployee(dat)}) //setData here
     }
  var employees=[]
  for (var i=0;i<employee.length;i++){
    var subarr={label:employee[i],value:employee[i]}
    employees.push(subarr)
  }

const CubicleIds = async () => {
  fetch(BASE_SERVICE_URL + '/cubicle/getCubicleIds', {
   method: 'get',
   headers:{"Access-Control-Allow-Origin": "*", 'Content-Type': 'application/json','Authorization': "Bearer " + localStorage.getItem('token')},
             }).then((response) => {
               return response.json();
           }).then(dat => {
             setcubicle(dat)}) //setData here
      }
      var cubicles=[]
      for (var i=0;i<cubicle.length;i++){
        var subarr={label:cubicle[i],value:cubicle[i]}
        cubicles.push(subarr)
      }
   
  return (
    <View style={styles.containerStyle}>
      <ScrollView contentContainerStyle={styles.scrollViewStyle}>
        <Text style={styles.Text}>Enter the Booking details to book Cubicle</Text>
        <View style={styles.headingStyle}>
          <FormBuilder
            control={control}
            setFocus={setFocus}
            formConfigArray={[
              {
                type: 'select',
                name: 'cubicleId',
                textInputProps: {
                  label: 'CubicleId',
                },
                rules: {
                  required: {
                    value: true,
                    message: 'cubicleId is required',
                  },
                },
                options: cubicles,
              },
              {
                type: 'select',
                name: 'employeeId',
                textInputProps: {
                  label: 'EmployeeId',
                },
                rules: {
                  required: {
                    value: true,
                    message: 'EmployeeId is required',
                  },
                },
                options: employees,
              },
              {
                type: 'text',
                name: 'startDate',
                textInputProps: {
                  label: 'StartDate (YYYY-MM-DD)',
                },
                rules: {
                  required: {
                    value: true,
                    message: 'StartDate is required',
                  },
                }
              },
              {
                type: 'text',
                name: 'endDate',
                textInputProps: {
                  label: 'EndDate (YYYY-MM-DD)',
                },
                rules: {
                  required: {
                    value: true,
                    message: 'EndDate is required',
                  },
                }
              },
            ]}
          />
          <Button
            mode={'contained'}
            onPress={handleSubmit(CreateBooking)}>
            CREATE
          </Button>
        </View>
      </ScrollView>
    </View>
  );
}


const styles = StyleSheet.create({
  containerStyle: {
    flex: 1,
  },
  scrollViewStyle: {
    flex: 1,
    padding: 5,
    justifyContent: 'center',
  },
  Text:{
    fontsize:120,
    fontWeight:'bold',
    textAlign:'center',
  },
  headingStyle: {
    fontSize: 20,
    textAlign: 'center',
    marginBottom: 40,
  },
});
export default BookingComponent;
