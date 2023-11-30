import React, { useState } from 'react';
import {
    View,
    Text,
    StyleSheet,
    Animated,
    TouchableHighlight,
    TouchableOpacity,
    StatusBar,
} from 'react-native';
import { SwipeListView } from 'react-native-swipe-list-view';
import MaterialCommunityIcons from 'react-native-vector-icons/MaterialCommunityIcons';
import SearchService from '../services/searchService';

const searchService = new SearchService();

const SearchScreenView = ({
    data,
    navigation,
    onClick,
}: {
    data: any;
    navigation: any;
    onClick: any;
}) => {
    const [listData, setListData] = useState(data);

    React.useEffect(() => {
        setListData(data);
    }, [data]);
    const closeRow = (rowMap: any, rowKey: any) => {
        if (rowMap[rowKey]) {
            rowMap[rowKey].closeRow();
        }
    };

    /* const deleteRow = async (rowMap, rowKey) => {
         closeRow(rowMap, rowKey);
         const newData = [...listData];
         const prevIndex = listData.findIndex((item) => item.key === rowKey);
         const deletedObj = listData.find((item) => item.key === rowKey);
         await notifService.deleteNotification(deletedObj.objectIdentifier, deletedObj.pk);
         newData.splice(prevIndex, 1);
         setListData(newData);
     };*/

    const onRowDidOpen = (rowKey) => {
        console.log('This row opened', rowKey);
    };

    const onLeftActionStatusChange = (rowKey) => {
        console.log('onLeftActionStatusChange', rowKey);
    };

    const onRightActionStatusChange = (rowKey) => {
        console.log('onRightActionStatusChange', rowKey);
    };

    const onRightAction = (rowKey) => {
        console.log('onRightAction', rowKey);
    };
    const onLeftAction = (rowKey) => {
        console.log('onLeftAction', rowKey);
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
                    onPress={() => onClick(data, data.item.key)}
                    underlayColor={'#aaa'}
                >
                    <View>
                        <Text style={styles.titleUnRead} numberOfLines={1}>
                            {data.item.title}
                        </Text>
                        <Text style={styles.details} numberOfLines={1}>
                            {data.item.details}
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

    const HiddenItemWithActions = (props) => {
        const {
            swipeAnimatedValue,
            leftActionActivated,
            rightActionActivated,
            rowActionAnimatedValue,
            rowHeightAnimatedValue,
            onClose,
            onDelete,
        } = props;

        if (rightActionActivated) {
            Animated.spring(rowActionAnimatedValue, {
                toValue: 500,
                useNativeDriver: false,
            }).start();
        } else {
            Animated.spring(rowActionAnimatedValue, {
                toValue: 75,
                useNativeDriver: false,
            }).start();
        }

        return (
            <Animated.View style={[styles.rowBack, { height: rowHeightAnimatedValue }]}>
                <Text>Left</Text>
                {!leftActionActivated && (
                    <Animated.View
                        style={[
                            styles.backRightBtn,
                            styles.backRightBtnRight,
                            {
                                flex: 1,
                                width: rowActionAnimatedValue,
                            },
                        ]}
                    >
                        <TouchableOpacity
                            style={[styles.backRightBtn, styles.backRightBtnRight]}
                            onPress={onDelete}
                        >
                            <Animated.View
                                style={[
                                    styles.trash,
                                    {
                                        transform: [
                                            {
                                                scale: swipeAnimatedValue.interpolate({
                                                    inputRange: [-90, -45],
                                                    outputRange: [1, 0],
                                                    extrapolate: 'clamp',
                                                }),
                                            },
                                        ],
                                    },
                                ]}
                            >
                                <MaterialCommunityIcons name='trash-can-outline' size={25} color='#fff' />
                            </Animated.View>
                        </TouchableOpacity>
                    </Animated.View>
                )}
            </Animated.View>
        );
    };

    const renderHiddenItem = (data: any, rowMap: any) => {
        const rowActionAnimatedValue = new Animated.Value(75);
        const rowHeightAnimatedValue = new Animated.Value(60);

        return (
            <HiddenItemWithActions
                data={data}
                rowMap={rowMap}
                rowActionAnimatedValue={rowActionAnimatedValue}
                rowHeightAnimatedValue={rowHeightAnimatedValue}
                onClose={() => closeRow(rowMap, data.item.key)}
            />
        );
    };

    return (
        <View style={styles.container}>
            <StatusBar barStyle='dark-content' />
            <SwipeListView
                data={listData}
                renderItem={renderItem}
                renderHiddenItem={renderHiddenItem}
                leftOpenValue={75}
                rightOpenValue={-150}
                disableRightSwipe
                onRowDidOpen={onRowDidOpen}
                leftActivationValue={100}
                rightActivationValue={-200}
                leftActionValue={0}
                rightActionValue={-500}
                onLeftAction={onLeftAction}
                onRightAction={onRightAction}
                onLeftActionStatusChange={onLeftActionStatusChange}
                onRightActionStatusChange={onRightActionStatusChange}
            />
        </View>
    );
};

export default SearchScreenView;

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
