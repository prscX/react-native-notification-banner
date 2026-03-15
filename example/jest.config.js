module.exports = {
  preset: 'react-native',
  transformIgnorePatterns: [
    'node_modules/(?!((jest-)?react-native|@react-native(-community)?|react-native-notification-banner|react-native-vector-icons|react-native-image-helper)/)',
  ],
};
