import React, { Component, useEffect, useState } from 'react';
import { View, Animated, PanResponder } from 'react-native';

export const SwipeGesture = ({
  onSwipePerformed,
  gestureStyle,
  children,
}: {
  onSwipePerformed: any;
  gestureStyle?: any;
  children?: any;
}) => {
  const panResponder = React.useMemo(
    () =>
      PanResponder.create({
        onStartShouldSetPanResponder: (evt, gestureState) => true,
        onPanResponderRelease: (evt, gestureState) => {
          let x = gestureState.dx;
          let y = gestureState.dy;
          if (Math.abs(x) > Math.abs(y)) {
            if (x >= 0) {
              onSwipePerformed('right');
            } else {
              onSwipePerformed('left');
            }
          } else {
            if (y >= 0) {
              onSwipePerformed('down');
            } else {
              onSwipePerformed('up');
            }
          }
        },
      }),
    []
  );

  return (
    <Animated.View {...panResponder.panHandlers} style={gestureStyle}>
      <View>{children}</View>
    </Animated.View>
  );
};
