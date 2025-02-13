<?php

namespace charly\infrastructure\repository;


use charly\core\domain\entity\Besoin;
use charly\infrastructure\repository\interfaces\BesoinRepositoryInterface;
use PDO;
use Psr\Container\ContainerInterface;
use charly\core\dto\BesoinDTO;


class BesoinRepository implements BesoinRepositoryInterface
{
    protected PDO $pdo;
    public function __construct(ContainerInterface $cont){
        $this->pdo=$cont->get('pdo');
    }
    public function createBesoin(string $clientId, int $idCompetence, string $libelle, int $timestamp): Besoin
    {
        $stmt = $this->pdo->prepare(
            'INSERT INTO besoin (client_id, competence_id, libelle, date) 
         VALUES (:client_id, :competence_id, :libelle, to_timestamp(:timestamp))'
        );
        $stmt->execute([
            'client_id'     => $clientId,
            'competence_id' => $idCompetence,
            'libelle'       => $libelle,
            'timestamp'     => $timestamp
        ]);
        $id = $this->pdo->lastInsertId();
        return new Besoin($clientId, $libelle, $idCompetence, $id, $timestamp);
    }

    
    public function getUserBesoins(string $id): array
    {
        
        $sql = "
            SELECT b.id, b.libelle, b.competence_id, b.date, u.nom AS nom_client, c.libelle AS competence_label
            FROM besoin b
            LEFT JOIN users u ON b.client_id = u.id
            LEFT JOIN competence c ON b.competence_id = c.id
            WHERE b.client_id = :client_id
        ";
    
        $stmt = $this->pdo->prepare($sql);
        $stmt->bindParam(':client_id', $id, PDO::PARAM_STR);  
        $stmt->execute();
        $rows = $stmt->fetchAll(PDO::FETCH_ASSOC);
    
        $besoins = [];
        foreach ($rows as $row) {
            $besoinId = $row['id'];
    
            
            $nomClient = $row['nom_client'];
            $libelleBesoin = $row['libelle'];
            $idCompetence = $row['competence_id'];
            $timestamp = strtotime($row['date']);  
    
            
            if (!isset($besoins[$besoinId])) {
                $besoins[$besoinId] = new Besoin(
                    $nomClient,
                    $libelleBesoin,
                    $idCompetence,
                    $besoinId,
                    $timestamp
                );
            }
    
   
        }
    
        
        $result = [];
        foreach ($besoins as $besoin) {
            $result[] = new BesoinDTO($besoin);
        }
    
        return $result;
    }
    

    public function getBesoins():array
    {
        $sql="
        SELECT b.id, b.libelle, b.competence_id, b.date, u.nom AS nom_client, c.libelle AS competence_label
            FROM besoin b
            LEFT JOIN users u ON b.client_id = u.id
            LEFT JOIN competence c ON b.competence_id = c.id";
        $stmt = $this->pdo->prepare($sql);

        $stmt->execute();
        $rows = $stmt->fetchAll(PDO::FETCH_ASSOC);

        $besoins = [];
        foreach ($rows as $row) {
            $besoinId = $row['id'];
    
            
            $nomClient = $row['nom_client'];
            $libelleBesoin = $row['libelle'];
            $idCompetence = $row['competence_id'];
            $timestamp = strtotime($row['date']);  
    
            
            if (!isset($besoins[$besoinId])) {
                $besoins[$besoinId] = new Besoin(
                    $nomClient,
                    $libelleBesoin,
                    $idCompetence,
                    $besoinId,
                    $timestamp
                );
            }
    
   
        }
    
        
        $result = [];
        foreach ($besoins as $besoin) {
            $result[] = new BesoinDTO($besoin);
        }
    
        return $result;
    

    }
        

}