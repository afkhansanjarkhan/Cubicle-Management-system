import React, { useState } from 'react';
import { View, StyleSheet, ScrollView, Text } from 'react-native';
import {
    Animated,
    TouchableHighlight,
  } from 'react-native';
  import { SwipeListView } from 'react-native-swipe-list-view';
import { FlatList, SafeAreaView, StatusBar, TouchableOpacity } from "react-native";


const BookingListComponent = ({
    bookings,
    navigation,
    onClickBooking,
  }: {
    bookings: any;
    navigation: any;
    onClickBooking: any;
  }) => {
    const [listData, setListData] = useState(bookings);
    React.useEffect(() => {
        setListData(bookings);
      }, [bookings]);
      const closeRow = (rowMap: any, rowKey: any) => {
        if (rowMap[rowKey]) {
          rowMap[rowKey].closeRow();
        }
      };

      const VisibleItem = (props) => {
        const { data, rowHeightAnimatedValue, removeRow, leftActionState, rightActionState } = props;
    
        if (rightActionState) {
          Animated.timing(rowHeightAnimatedValue, {
            toValue: 0,
            duration: 200,
            useNativeDriver: false,
          }).start(() => {
            removeRow();
          });
        }
    
        return (
          <Animated.View style={[styles.rowFront, { height: rowHeightAnimatedValue }]}>
            <TouchableHighlight
              style={styles.rowFrontVisible}
              onPress={() => onClickBooking(data, data.item.key)}
              underlayColor={'#aaa'}
            >
              <View>
                  <Text style={styles.titleUnRead} numberOfLines={1}>
                    Booking for Cubicle {data.item.cubicleId}
                  </Text>
                <Text style={styles.details} numberOfLines={1}>
                  {data.item.employeeId}
                </Text>
              </View>
            </TouchableHighlight>
          </Animated.View>
        );
      };
      const renderItem = (data, rowMap) => {
        const rowHeightAnimatedValue = new Animated.Value(60);
    
        return (
          <VisibleItem
            data={data}
            rowHeightAnimatedValue={rowHeightAnimatedValue}
          />
        );
      };
      return (
        <View style={styles.container}>
          <StatusBar barStyle='dark-content' />
          <SwipeListView
            data={listData}
            renderItem={renderItem}
            leftOpenValue={75}
            rightOpenValue={-150}
            disableRightSwipe
            leftActivationValue={100}
            rightActivationValue={-200}
            leftActionValue={0}
            rightActionValue={-500}

          />
        </View>
      );

}
export default BookingListComponent;


const styles = StyleSheet.create({
    container: {
      backgroundColor: '#f4f4f4',
      flex: 1,
    },
    backTextWhite: {
      color: '#FFF',
    },
    rowFront: {
      backgroundColor: '#FFF',
      borderRadius: 5,
      height: 60,
      margin: 5,
      marginBottom: 15,
      shadowColor: '#999',
      shadowOffset: { width: 0, height: 1 },
      shadowOpacity: 0.8,
      shadowRadius: 2,
      elevation: 5,
    },
    rowFrontVisible: {
      backgroundColor: '#FFF',
      borderRadius: 5,
      height: 60,
      padding: 10,
      marginBottom: 15,
    },
    rowBack: {
      alignItems: 'center',
      backgroundColor: '#DDD',
      flex: 1,
      flexDirection: 'row',
      justifyContent: 'space-between',
      paddingLeft: 15,
      margin: 5,
      marginBottom: 15,
      borderRadius: 5,
    },
    backRightBtn: {
      alignItems: 'flex-end',
      bottom: 0,
      justifyContent: 'center',
      position: 'absolute',
      top: 0,
      width: 75,
      paddingRight: 17,
    },
    backRightBtnLeft: {
      backgroundColor: '#1f65ff',
      right: 75,
    },
    backRightBtnRight: {
      backgroundColor: 'red',
      right: 0,
      borderTopRightRadius: 5,
      borderBottomRightRadius: 5,
    },
    trash: {
      height: 15,
      width: 13,
      marginRight: 7,
    },
    titleRead: {
      fontSize: 14,
      fontWeight: 'bold',
      marginBottom: 5,
      color: '#666',
    },
    titleUnRead: {
      fontSize: 14,
      fontWeight: 'bold',
      marginBottom: 5,
      color: 'blue',
    },
    details: {
      fontSize: 12,
      color: '#999',
    },
  
    detailscontainer: {
      flex: 1,
      alignItems: 'center',
      justifyContent: 'center',
    },
    separator: {
      marginVertical: 30,
      height: 1,
      width: '80%',
    },
  });
  