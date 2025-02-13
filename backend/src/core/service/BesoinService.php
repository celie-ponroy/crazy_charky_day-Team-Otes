<?php

namespace charly\core\service;

use charly\core\dto\BesoinDTO;
use charly\core\dto\input\InputCreateBesoinDTO;
use charly\core\service\interfaces\BesoinServiceInterface;
use charly\infrastructure\repository\interfaces\BesoinRepositoryInterface;
use charly\infrastructure\repository\interfaces\UserRepositoryInterface;
use DateTime;

class BesoinService implements BesoinServiceInterface
{
    protected BesoinRepositoryInterface $besoinRepository;
    protected UserRepositoryInterface $userRepository;

    public function __construct(BesoinRepositoryInterface $besoinRepository, UserRepositoryInterface $userRepository)
    {
        $this->besoinRepository = $besoinRepository;
        $this->userRepository = $userRepository;
    }

    public function createBesoin(InputCreateBesoinDTO $inputCreateBesoinDTO): BesoinDTO
    {
        $timestamp = (new DateTime())->getTimestamp() ;
        $idCient = $this->userRepository->getUserIdByNom($inputCreateBesoinDTO->getNomClient());
        $besoin = $this->besoinRepository->createBesoin($idCient ,$inputCreateBesoinDTO->getIdCompetence(), $inputCreateBesoinDTO->getLibelleBesoin(), $timestamp);
        return new BesoinDTO($besoin);
    }
}