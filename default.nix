# other pacakges that you might need:
# docker
# docker-compose
# idea
# vscode-with-extensions 

with import <nixpkgs> {};
stdenv.mkDerivation rec {
  name = "env";
  env = buildEnv { name = name; paths = buildInputs; };
  buildInputs = [
    maven
    platformio
  ];
}
