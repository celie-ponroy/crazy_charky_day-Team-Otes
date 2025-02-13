<?php
namespace charly\core\dto;

use charly\core\dto\DTO;

class UserDTO extends DTO {
    protected string $id;
    protected string $nom;
    protected string $hashed_password;
    protected int $role;
    protected string $accessToken;
    protected string $refreshToken;

    public function __construct(string $id, string $nom, string $hashed_password, int $role) {
        $this->id = $id;
        $this->nom = $nom;
        $this->hashed_password = $hashed_password;
        $this->role = $role;
        $this->accessToken = "";
        $this->refreshToken = "";
    }

    public function setAccessToken(string $accessToken): void {
        $this->accessToken = $accessToken;
    }

    public function setRefreshToken(string $refreshToken): void {
        $this->refreshToken = $refreshToken;
    }
}
