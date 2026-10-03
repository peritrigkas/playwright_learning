export default {
  name: "Allure Report",
  output: "./allure-report",
  plugins: {
    awesome: {
      options: {
        singleFile: true,
      },
    },
  },
};
