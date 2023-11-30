import React, { useEffect, useState } from 'react';
import { View, StyleSheet, ScrollView, Text } from 'react-native';
import { FormBuilder } from 'react-native-paper-form-builder';
import { useForm } from 'react-hook-form';
import { Button } from 'react-native-paper';
import CubicleService from '../services/cubicleService';
import { number } from 'prop-types';
import { getEnumAsObjectArray } from '../utils/boostrapUtils';
import { BASE_SERVICE_URL } from '../config';






const cubicleService = new CubicleService();
function CubicleComponent() {
  const[location,setLocationName]=useState([]);

  const onCreate = (data: { cubicleId: string; locationName: string; status: string; }) => {
    cubicleService.create(data.cubicleId, data.locationName, data.status).then();
  };
 
  const { control, setFocus, formState: { errors }, handleSubmit } = useForm({
    mode: 'onChange',
  });

  useEffect(() => {
    Location();
},[])
const Location = async () => {
  fetch(BASE_SERVICE_URL + '/location/getLocationNames', {
   method: 'get',
   headers:{"Access-Control-Allow-Origin": "*", 'Content-Type': 'application/json','Authorization': "Bearer " + localStorage.getItem('token')},
             }).then((response) => {
               return response.json();
           }).then(dat => {
             setLocationName(dat)}) //setData here
      }
   var locations=[]
   for (var i=0;i<location.length;i++){
     var subarr={label:location[i],value:location[i]}
     locations.push(subarr)
   }
  
  return (
    <View style={styles.containerStyle}>
      <ScrollView contentContainerStyle={styles.scrollViewStyle}>
        <Text>Enter the Cubicle details to create Cubicle</Text>
        <View style={styles.headingStyle}>
          <FormBuilder
            control={control}
            setFocus={setFocus}
            formConfigArray={[
              {
                type: 'text',
                name: 'cubicleId',
                textInputProps: {
                  label: 'CubicleId',
                },
                rules: {
                  required: {
                    value: true,
                    message: 'cubicleId is required',
                  },
                }
              },
              {
                type: 'select',
                name: 'status',
                textInputProps: {
                  label: 'Status',
                },
                rules: {
                  required: {
                    value: true,
                    message: 'Status is required',
                  },
                },
                options: getEnumAsObjectArray('com.modeln.spaceit.enums.CSICubicleStatus'),
              },
              {
                type: 'select',
                name: 'locationName',
                textInputProps: {
                  label: 'LocationName',
                },
                rules: {
                  required: {
                    value: true,
                    message: 'Location is required',
                  },
                },
                options: locations,
              },
            ]}
          />
          <Button
            mode={'contained'}
            onPress={handleSubmit(onCreate)}>
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
  headingStyle: {
    fontSize: 10,
    textAlign: 'center',
    marginBottom: 40,
  },
});
export default CubicleComponent;