# Other packages that you might need:
# docker
# idea
# vscode-with-extensions 

with import <nixpkgs> {};
stdenv.mkDerivation rec {
  name = "env";
  env = buildEnv { name = name; paths = buildInputs; };
  buildInputs = [
    maven
    platformio
    docker_compose
  ];
}
