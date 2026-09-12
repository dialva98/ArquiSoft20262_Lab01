import react from "@vitejs/plugin-react";
import { defineConfig } from "vite";

export default defineConfig({
  plugins: [react()],

  build: {
    outDir: "../src/main/resources/static",
    emptyOutDir: true,
  },
});
