# Utility classes

## Goal 
This project aims to provide drop-in replacement for existing duplicated code across projects
"Standard way of doing same thing"

This project does not rely on clients to provide any special configuration to work, 
However might provide configuration hooks to modify behaviour

## Version management
Major.minor.patch (ex: 1.0.0)

### Major: Breaking changes
Bump major version if this release breaks any contract
That is to say: "This version may not be compatible with existing clients"

### Minor: Adding or removing features without compatibility problems
Bump minor version if this release DOES NOT break any contract,
instead add/ remove features in compatible way

### path: Fixing issues, improvements
Bump patch version every time except Major/minor version change


## General guidelines

- make appropriate package for given functionality
- don't expose too many interfaces
- abstract implementation details as much as possible
- Use `Factory classes` to return an Object, which can be switched easily in the future
without breaking the clients

## Modules
- Password encoders documentation [here](src/main/java/com/fable/password/README.md)
