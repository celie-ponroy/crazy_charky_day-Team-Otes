<?php

namespace charly\application\action;

use charly\application\renderer\JsonRenderer;
use charly\core\dto\input\InputCreateBesoinDTO;
use charly\core\service\interfaces\BesoinServiceInterface;
use Psr\Http\Message\ResponseInterface;
use Psr\Http\Message\ServerRequestInterface;
use InvalidArgumentException;
use Throwable;

class PostBesoin extends AbstractAction
{
    protected BesoinServiceInterface $besoinService;

    public function __construct(BesoinServiceInterface $bs)
    {
        $this->besoinService = $bs;
    }

    public function __invoke(ServerRequestInterface $rq, ResponseInterface $rs, array $args): ResponseInterface
    {
        try {
            $data = $rq->getParsedBody();
            
            if (!is_array($data)) {
                throw new InvalidArgumentException("Invalid request body format.");
            }

            
            $nomClient = isset($data['nom_client']) ? htmlspecialchars(strip_tags($data['nom_client'])) : null;
            $idCompetence = isset($data['id_competence']) ? filter_var($data['id_competence'], FILTER_VALIDATE_INT) : null;
            $libelleBesoin = isset($data['libelle_besoin']) ? htmlspecialchars(strip_tags($data['libelle_besoin'])) : null;

            if (empty($nomClient) || !is_string($nomClient)) {
                throw new InvalidArgumentException("Le nom du client est requis et doit être une chaîne de caractères.");
            }
            if (empty($idCompetence) || !is_int($idCompetence)) {
                throw new InvalidArgumentException("L'ID de compétence est requis et doit être un nombre entier valide.");
            }
            if (empty($libelleBesoin) || !is_string($libelleBesoin)) {
                throw new InvalidArgumentException("Le libellé du besoin est requis et doit être une chaîne de caractères.");
            }

            $besoin = $this->besoinService->createBesoin(new InputCreateBesoinDTO($nomClient, $idCompetence, $libelleBesoin));
            return JsonRenderer::render($rs, 200, $besoin);
        } catch (InvalidArgumentException $e) {
            return JsonRenderer::render($rs, 400, ["error" => $e->getMessage()]);
        } catch (Throwable $e) {
            return JsonRenderer::render($rs, 500, ["error" => "Une erreur interne est survenue.", "details" => $e->getMessage()]);
        }
    }
}
