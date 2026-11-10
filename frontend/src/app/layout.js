import NavBar from "@/components/NavBar";
import "./globals.css";

export const metadata = {
  title: "Codemm",
  description: "Practice programming with guided AI hints, automated checks and community support.",
};

export default function RootLayout({ children }) {
  return (
    <html lang="en">
      <body>
        <NavBar />
        {children}
      </body>
    </html>
  );
}
